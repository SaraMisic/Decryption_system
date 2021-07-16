import java.util.ArrayList;
import java.util.List;

public class Subjects {
    private List<Subject> subjects;

    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    public void setSubjects() {
        subjects = new ArrayList<Subject>();
    }

    public float average_grade()
    {
        int result = subjects.stream().reduce(0,(partialGradeResult,student)->partialGradeResult+student.getGrade(),Integer::sum);

        return (float) result/(subjects.size());
    }

    public int espb_sum()
    {
        return subjects.stream().reduce(0,(partialGradeResult,student)->partialGradeResult+student.getEspb(),Integer::sum);
    }

}
