package com.nothinq.database.repository.object;

import java.util.List;

public abstract class Page<T> {

    public abstract List<T> getContent();

    public abstract int getPage();

    public abstract int getSize();

    public abstract long getTotalElement();
}
