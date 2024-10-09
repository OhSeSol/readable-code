package cleancode.studycafe.tobe.model;


import java.util.ArrayList;
import java.util.List;

public class StudyCafePasses {

    private List<StudyCafePass> studyCafePasses;

    private StudyCafePasses(List<StudyCafePass> studyCafePasses) {
        this.studyCafePasses = studyCafePasses;
    }

    public static StudyCafePasses of(List<StudyCafePass> studyCafePasses){
        return new StudyCafePasses(studyCafePasses);
    }

    public StudyCafePasses filterByPassType(StudyCafePassType passType) {
        List<StudyCafePass> filteredStudyCafePasses = studyCafePasses.stream()
                .filter(studyCafePass -> studyCafePass.isPassTypeEqualTo(passType))
                .toList();

        return StudyCafePasses.of(filteredStudyCafePasses);
    }

    public List<StudyCafePass> getStudyCafePasses() {
        return new ArrayList<>(studyCafePasses);
    }

    public int getSize(){
        return studyCafePasses.size();
    }

    public StudyCafePass getIndexOf(int index){
        return studyCafePasses.get(index);
    }
}
