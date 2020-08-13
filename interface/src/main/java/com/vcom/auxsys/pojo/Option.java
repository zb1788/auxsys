package com.vcom.auxsys.pojo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Option {
    private String optionid;
    private String optionname;
    private String optionorder;
    private String results;

    public String getOptionid() {
        return optionid;
    }

    public void setOptionid(String optionid) {
        this.optionid = optionid;
    }

    public String getOptionname() {
        return optionname;
    }

    public void setOptionname(String optionname) {
        this.optionname = optionname;
    }

    public String getOptionorder() {
        return optionorder;
    }

    public void setOptionorder(String optionorder) {
        this.optionorder = optionorder;
    }

    public String getResults() {
        return results;
    }

    public void setResults(String results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "Option{" +
                "optionid='" + optionid + '\'' +
                ", optionname='" + optionname + '\'' +
                ", optionorder='" + optionorder + '\'' +
                ", results='" + results + '\'' +
                '}';
    }
}
