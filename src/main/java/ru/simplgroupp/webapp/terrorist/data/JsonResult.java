package ru.simplgroupp.webapp.terrorist.data;

/**
 * 23.07.2015
 * 13:54
 */

public class JsonResult {
    private boolean success;
    private Object data;

    public JsonResult(Object data) {
        success = true;
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
