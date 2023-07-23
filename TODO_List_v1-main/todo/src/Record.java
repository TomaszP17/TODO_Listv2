import java.time.LocalDate;
import java.util.Date;

public class Record {
    private int idRecord;
    private String content;
    private boolean isDone;
    private int priority;
    private LocalDate createDate;
    public Record(int idRecord, String content, boolean isDone, int priority, LocalDate createDate) {
        this.idRecord = idRecord;
        this.content = content;
        this.isDone = isDone;
        this.priority = priority;
        this.createDate = createDate;
    }
    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
    public int getPriority() {
        return priority;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }
}
