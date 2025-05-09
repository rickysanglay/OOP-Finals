package package01;

import java.util.UUID;

public abstract class Person {
    private String name;
    private String idNumber;
    private String personId;

    public Person(String name, String idNumber) {
        this.name = name;
        this.idNumber = idNumber;
        this.personId = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getPersonId() {
        return personId;
    }
}