package ru.simplgroupp.webapp.terrorist.data;

/**
 * 23.07.2015
 * 12:42
 */

/**
 * Типы парсируемых страниц
 * http://fedsfm.ru/documents/terrorists-catalog-portal-act - действующие террористы
 * http://fedsfm.ru/documents/terrorists-catalog-portal-add - новые террористы
 * http://fedsfm.ru/documents/terrorists-catalog-portal-del - исключенные террористы
 */
public enum ParsePageTypes {
    ACT("http://fedsfm.ru/documents/terrorists-catalog-portal-act"),
    ADD("http://fedsfm.ru/documents/terrorists-catalog-portal-add"),
    DEL("http://fedsfm.ru/documents/terrorists-catalog-portal-del");

    private String url;

    ParsePageTypes(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
