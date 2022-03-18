package com.bol.mancala.utils;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author mir00r on 8/2/22
 * @project IntelliJ IDEA
 */
public class PageAttr {
    public static final int PAGE_SIZE = 10;

    public static final String SORT_BY_FIELD_ID = "id";

    public static PageRequest getPageRequest(int page) {
        return PageRequest.of(Math.abs(page), PageAttr.PAGE_SIZE, Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID);
    }

    public static PageRequest getPageRequest(int page, int size) {
        if (Math.abs(size) > 100) size = 100;
        return PageRequest.of(Math.abs(page), Math.abs(size), Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID);
    }

    public static PageRequest getPageRequestExact(int page, int size) {
        return PageRequest.of(Math.abs(page), Math.abs(size), Sort.Direction.DESC, PageAttr.SORT_BY_FIELD_ID);
    }

    public static PageRequest getPageRequestExactWithSort(int page, int size, Sort.Direction direction, String fieldName) {
        return PageRequest.of(Math.abs(page), Math.abs(size), direction, fieldName);
    }

    public static PageRequest getPageRequestWithSort(int page, int size, Sort.Direction direction, String fieldName) {
        if (Math.abs(size) > 100)
            size = 100;
        return PageRequest.of(Math.abs(page), Math.abs(size), direction, fieldName);
    }

    public static PageRequest getLimitlessPageRequestWithSort(int page, int size, Sort.Direction direction, String fieldName) {
        return PageRequest.of(Math.abs(page), Math.abs(size), direction, fieldName);
    }
}
