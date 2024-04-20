package word;
import java.util.Objects;

public class Word {
    private String word_target;
    private String word_explain;

    public Word() {
        this.word_explain = "";
        this.word_target = "";
    }

    public Word(String word_target, String word_explain) {
        this.word_target = word_target;
        this.word_explain = word_explain;
    }

    public void setWordExplain(String word_explain) {
        this.word_explain = word_explain;
    }

    public String getWordExplain() {
        return word_explain;
    }

    public void setWordTarget(String word_target) {
        this.word_target = word_target;
    }

    public String getWordTarget() {
        return word_target;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Word)) return false;
        Word word = (Word) o;
        return Objects.equals(word_target, word.word_target);
    }

    @Override
    public int hashCode() {
        return Objects.hash(word_target, word_explain);
    }

    @Override
    public String toString() {
        return "Word{" + "wordTarget='" + word_target + '\'' + ",wordExplain='"
                + word_explain + '\'' + '}';
    }

}

