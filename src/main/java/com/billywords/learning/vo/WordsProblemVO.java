package com.billywords.learning.vo;

public class WordsProblemVO {
    String chooseExampleId;
    String status;
    boolean isNextExample;

    public String getChooseExampleId() {
        return chooseExampleId;
    }

    public void setChooseExampleId(String chooseExampleId) {
        this.chooseExampleId = chooseExampleId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNextExample() {
        return isNextExample;
    }

    public void setNextExample(boolean isNextExample) {
        isNextExample = isNextExample;
    }
}
