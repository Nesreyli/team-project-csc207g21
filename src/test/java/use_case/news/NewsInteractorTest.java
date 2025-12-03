package use_case.news;

import entity.News;
import entity.Response;
import org.junit.jupiter.api.Test;
import use_case.news.NewsAccessInterface;
import use_case.news.NewsInteractor;
import use_case.news.NewsOutputBoundary;
import use_case.news.NewsOutputData;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit tests for news feed
 */
public class NewsInteractorTest {
    static class NewsAccess implements NewsAccessInterface {
        private final Response response;

        NewsAccess(Response response) {
            this.response = response;
        }

        @Override
        public Response getNews() {
            return response;
        }
    }

    static class OutputBoundary implements NewsOutputBoundary {
        NewsOutputData successData = null;
        String failMessage = null;

        @Override public void prepareSuccessView(NewsOutputData data){
            this.successData = data;
        }

        @Override
        public void prepareFailView(String message) {
            this.failMessage = message;
        }
    }
    @Test
    void testSuccessCase(){
        List<News> newsList = new ArrayList<>();
        News news = new News();
        news.setTitle("Title 1");
        news.setAuthor("Author 1");
        news.setDate("Date 1");
        news.setContent("Content 1");
        newsList.add(news);
        Response response = new Response(200, newsList);
        NewsAccess newsAccess = new NewsAccess(response);
        OutputBoundary outputBoundary = new OutputBoundary();
        NewsInteractor newsInteractor = new NewsInteractor(newsAccess, outputBoundary);

        newsInteractor.execute();

        assert (outputBoundary.successData != null);
        assert (outputBoundary.failMessage == null);
        assert (outputBoundary.successData.getNewsList().size() == 1);
    }

    @Test
    void testSetNewsList(){
        List<News> newsList = new ArrayList<>();
        List<News> secondList = new ArrayList<>();
        NewsOutputData outputData = new NewsOutputData(newsList);
        outputData.setNewsList(secondList);
        assert (outputData.getNewsList() == secondList);
    }

    @Test
    void test400Error() {
        Response response = new Response(400,null);
        NewsAccess newsAccess = new NewsAccess(response);
        OutputBoundary outputBoundary = new OutputBoundary();
        NewsInteractor  newsInteractor = new NewsInteractor(newsAccess, outputBoundary);
        newsInteractor.execute();

        assert (outputBoundary.successData == null);
        System.out.println("fail message " + outputBoundary.failMessage);
        assert (outputBoundary.failMessage.equals("News error"));
    }

    @Test
    void testRuntimeException() {
        Response response = new Response(500, null);
        NewsAccess newsAccess = new NewsAccess(response) {
            @Override
            public Response getNews(){
                throw new RuntimeException("Test 123");
            }
        };
        OutputBoundary outputBoundary = new OutputBoundary();
        NewsInteractor newsInteractor = new NewsInteractor(newsAccess, outputBoundary);

        newsInteractor.execute();

        assert (outputBoundary.successData == null);
        assert (outputBoundary.failMessage.equals("Server Error"));

    }

}
