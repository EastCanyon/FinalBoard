package com.example.finalboard.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PagingUtils {

    public static PagingInfo getPagingInfo(String currentPage_, String pageSize_, long totalRowDataCnt) {
        int pageBlockCnt = 5;
        int pageSize = checkPageSizeValidation(pageSize_);
        int totalPageBlockCnt = calTotalPageBlockCnt(totalRowDataCnt, pageSize);
        int currentPage = checkCurrentPageValidation(Integer.parseInt(currentPage_), totalPageBlockCnt);

        int endPageBlock = calEndPageBlock(currentPage, pageBlockCnt);
        int startPageBlock = calStartPageBlock(endPageBlock, pageBlockCnt);
        endPageBlock = checkEndPageBlockValidation(endPageBlock, totalPageBlockCnt);
        boolean next = (currentPage != totalPageBlockCnt);

        startPageBlock = checkStartPageBlockValidation(startPageBlock);
        boolean prev = (currentPage != 1);

        int startRowDataNum = (currentPage - 1) * pageSize + 1;
        int endRowDataNum = startRowDataNum + pageSize - 1;

        return new PagingInfo(
                pageSize,
                currentPage,
                startPageBlock,
                endPageBlock,
                startRowDataNum,
                endRowDataNum,
                totalPageBlockCnt,
                totalRowDataCnt,
                prev,
                next
        );
    }

    private static int checkPageSizeValidation(String pageSize) {
        if ("5".equals(pageSize) || "10".equals(pageSize) || "30".equals(pageSize) || "50".equals(pageSize)) {
            return Integer.parseInt(pageSize);
        }
        return 10;
    }

    private static int checkCurrentPageValidation(int currentPage, int totalPageBlockCnt) {
        if (currentPage > totalPageBlockCnt)
            currentPage = totalPageBlockCnt;
        else if (currentPage < 1)
            currentPage = 1;

        return currentPage;
    }

    private static int calTotalPageBlockCnt(long totalRowDataCnt, int pageSize) {
        if (totalRowDataCnt == 0) {
            return 1;
        }
        return (int) (totalRowDataCnt + pageSize - 1) / pageSize;
    }

    private static int calEndPageBlock(int currentPage, int pageBlockCnt) {
        return ((currentPage + pageBlockCnt - 1) / pageBlockCnt) * pageBlockCnt;
    }

    private static int checkEndPageBlockValidation(int endPageBlock, int totalPageBlockCnt) {
        return Math.min(endPageBlock, totalPageBlockCnt);
    }

    private static int calStartPageBlock(int endPageBlock, int pageBlockCnt) {
        return endPageBlock - pageBlockCnt + 1;
    }

    private static int checkStartPageBlockValidation(int startPageBlock) {
        return Math.max(startPageBlock, 1);
    }

    @Getter
    @AllArgsConstructor
    public static class PagingInfo {
        private int pageSize;
        private int currentPage;
        private int startPageBlock;
        private int endPageBlock;
        private int startRowDataNum;
        private int endRowDataNum;
        private int totalPageBlockCnt;
        private long totalRowDataCnt;
        private boolean prev;
        private boolean next;
    }
}
