package ui.dto;

import lombok.Builder;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ui.wrappers.*;

@Getter
@Builder
public class FormData {

    private static final Logger logger = LoggerFactory.getLogger(FormData.class);

    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private String subjects;
    private String currentAddress;
    private String permanentAddress;
    private String age;
    private String salary;
    private String department;

    // Главный метод заполнения всех полей
    public FormData fill(WebDriver driver) {
        logger.info("Filling form with data: {}", this);

        new Input(driver, "First Name").write(firstName);
        new Input(driver, "Last Name").write(lastName);
        new Input(driver, "Email").write(email);
        new Input(driver, "Mobile").write(mobile);

        if (subjects != null) {
            new Input(driver, "Subjects").write(subjects);
        }

        if (currentAddress != null) {
            new TextArea(driver, "Current Address").write(currentAddress);
        }

        if (permanentAddress != null) {
            new TextArea(driver, "Permanent Address").write(permanentAddress);
        }

        return this;
    }

    // Для полей на отдельной странице/вкладке
    public FormData fillWebPageFields(WebDriver driver) {
        logger.info("Filling web page fields");

        if (lastName != null) new Input(driver, "Last Name").write(lastName);
        if (email != null) new Input(driver, "Email").write(email);
        if (age != null) new Input(driver, "Age").write(age);
        if (salary != null) new Input(driver, "Salary").write(salary);
        if (department != null) new Input(driver, "Department").write(department);

        return this;
    }

    // Специфичные поля (радиокнопки, дропдауны)
    public FormData fillSpecialFields(WebDriver driver, String gender, String stateAndCity) {
        logger.info("Filling special fields: gender={}, location={}", gender, stateAndCity);

        new RadioButton(driver, gender).select();
        new PickList(driver, "State and City").select(stateAndCity);

        return this;
    }

    // Чекбоксы
    public FormData checkDefaultCheckboxes(WebDriver driver) {
        logger.info("Checking default checkboxes");

        new Checkbox(driver, "Desktop").check();
        new Checkbox(driver, "Documents").check();
        new Checkbox(driver, "Downloads").check();
        new Checkbox(driver, "Home").check();

        return this;
    }

    // Утилитный метод для быстрого заполнения адресов
    public FormData fillAddresses(WebDriver driver) {
        if (currentAddress != null) {
            new TextArea(driver, "Current Address").write(currentAddress);
        }
        if (permanentAddress != null) {
            new TextArea(driver, "Permanent Address").write(permanentAddress);
        }
        return this;
    }
}
