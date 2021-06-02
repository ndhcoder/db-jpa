package com.nothinq.database.repository.object;

import java.util.List;

public class PageImpl<T> extends Page<T> {
    List<T> content;
    long totalElement;
    int page;
    int size;

    public PageImpl(List<T> content, long totalElement, Pageable pageable) {
        this.content = content;
        this.totalElement = totalElement;
        this.page = pageable.getPage();
        this.size = pageable.getSize();
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }

    public long getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(int totalElement) {
        this.totalElement = totalElement;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
