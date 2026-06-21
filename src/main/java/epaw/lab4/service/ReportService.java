package epaw.lab4.service;

import epaw.lab4.model.ReportedItem;
import epaw.lab4.repository.ReportRepository;

import java.util.List;
import java.util.Map;

public class ReportService {

    private static ReportService instance;
    private final ReportRepository reportRepository;

    private ReportService() {
        this.reportRepository = ReportRepository.getInstance();
    }

    public static synchronized ReportService getInstance() {
        if (instance == null) instance = new ReportService();
        return instance;
    }

    public boolean report(int tweetId, int reporterId, String reason) {
        return reportRepository.saveReport(tweetId, reporterId, reason);
    }

    public List<ReportedItem> getReportedPosts() {
        return reportRepository.getReportedPosts();
    }

    public List<ReportedItem> getReportedComments() {
        return reportRepository.getReportedComments();
    }

    public void deleteTweet(int tweetId) {
        reportRepository.ignoreReport(tweetId);
        reportRepository.deleteTweet(tweetId);
    }

    public void banUser(int userId, int tweetId) {
        reportRepository.banUser(userId);
        reportRepository.ignoreReport(tweetId);
    }

    public void ignoreReport(int tweetId) {
        reportRepository.ignoreReport(tweetId);
    }

    public Map<String, Integer> getStats() {
        return reportRepository.getStats();
    }
}
