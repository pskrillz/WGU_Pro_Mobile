package com.pi.wgu_pro.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "TermTable")
public class Term {
    @PrimaryKey(autoGenerate = true) private int termId;
    @ColumnInfo(name = "TermName") private String termName;
    @ColumnInfo(name = "TermStatus") private String termStatus;
    @ColumnInfo(name = "TermStart") private Date termStart;
    @ColumnInfo(name = "TermEnd") private Date termEnd;

    public int getTermId() {
        return termId;
    }

    public String getTermName() {
        return termName;
    }

    public String getTermStatus() {
        return termStatus;
    }

    public Date getTermStart() {
        return termStart;
    }

    public Date getTermEnd() {
        return termEnd;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public void setTermName(String termName) {
        this.termName = termName;
    }

    public void setTermStatus(String termStatus) {
        this.termStatus = termStatus;
    }

    public void setTermStart(Date termStart) {
        this.termStart = termStart;
    }

    public void setTermEnd(Date termEnd) {
        this.termEnd = termEnd;
    }

    @Override
    public String toString() {
        return this.getTermName();
    }
}
