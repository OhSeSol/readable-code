package cleancode.studycafe.tobe;

import cleancode.studycafe.tobe.exception.AppException;
import cleancode.studycafe.tobe.io.*;
import cleancode.studycafe.tobe.model.StudyCafeLockerPass;
import cleancode.studycafe.tobe.model.StudyCafePass;
import cleancode.studycafe.tobe.model.StudyCafePassType;
import cleancode.studycafe.tobe.model.StudyCafePasses;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;
    private final StudyCafeFileHandler studyCafeFileHandler;

    public StudyCafePassMachine(InputHandler inputHandler, OutputHandler outputHandler, StudyCafeFileHandler studyCafeFileHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
        this.studyCafeFileHandler = studyCafeFileHandler;
    }

    public void run() {
        try {
            outputHandler.showWelcomeMessage();
            outputHandler.showAnnouncement();

            outputHandler.askPassTypeSelection();
            StudyCafePassType selectedPassType = inputHandler.getPassTypeSelectingUserAction();

            StudyCafePasses studyCafePasses = studyCafeFileHandler.readStudyCafePasses();
            StudyCafePasses selectedTypePasses = studyCafePasses.filterByPassType(selectedPassType);
            outputHandler.showPassListForSelection(selectedTypePasses);

            StudyCafePass selectedPass = inputHandler.getSelectPass(selectedTypePasses);
            StudyCafeLockerPass lockerPass = getLockerPass(selectedPassType, selectedPass);

            outputHandler.showPassOrderSummary(selectedPass, lockerPass);

        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }
    }

    private StudyCafeLockerPass getLockerPass(StudyCafePassType selectedPassType, StudyCafePass selectedPass) {
        if (isNotFixedPassType(selectedPassType)) {
            return null;
        }

        StudyCafeLockerPass selectedTypeLockerPass = studyCafeFileHandler.getSelectedTypeLockerPass(selectedPass);
        outputHandler.askLockerPass(selectedTypeLockerPass);

        boolean lockerSelection = inputHandler.getLockerSelection();
        if (lockerSelection) {
            return selectedTypeLockerPass;
        }

        return null;
    }


    private boolean isNotFixedPassType(StudyCafePassType selectedPassType) {
        return selectedPassType != StudyCafePassType.FIXED;
    }

}
