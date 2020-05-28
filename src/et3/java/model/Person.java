package et3.java.model;

/**
 * Person is the abstract base class for all classes that could represent
 * somebody.
 * A Person object encapsulates a firstname and a lastname.
 */
public abstract class Person {
    private String name;
    private String surname;

    /**
     * Constructs an empty <tt>Person</tt> with the specified name and surname.
     * @param name      string corresponding to the name given to the Person.
     * @param surname   string corresponding to the surname given to the Person.
     */
    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}