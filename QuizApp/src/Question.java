public class Question {
    private String ques;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correct=1;

    Question(String q,String a,String b,String c,String d,int correct){
        ques=q;
        option1=a;
        option2=b;
        option3=c;
        option4=d;
        this.correct=correct;
    }

    public String getQues() {
        return ques;
    }
    public String getOption(int i){
        switch(i){
            case 1: return option1;
            case 2: return option2;
            case 3: return option3;
            case 4: return option4;
            default: return "error";
        }
    }

    public int getCorrect() {
        return correct;
    }
}
