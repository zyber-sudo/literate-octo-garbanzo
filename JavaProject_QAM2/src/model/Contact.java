package model;

/**
 * Class for creating an Contact object.
 *
 * <p>
 * This class declares and defines the fields and methods for a Contact.
 * </p>
 *
 * @author Zachary Zamiska
 * @version JDK 11.0
 */
public class Contact {

    private final int contactId;
    private final String contactName;
    private final String contactEmail;


    /**
     * Base constructor for the Contact class.
     *
     * @param contactId    The ID of the contact
     * @param contactName  The name of the contact
     * @param contactEmail The email of the contact
     */
    public Contact(int contactId, String contactName, String contactEmail) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.contactEmail = contactEmail;
    }

    /**
     * @return the contacts ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * @return the contacts name
     */
    public String getContactName() {
        return contactName;
    }

    /**
     * @return the contacts email
     */
    public String getContactEmail() {
        return contactEmail;
    }

    /**
     * The overload for the toString method for the Contact.
     *
     * <p>
     * This method overloads the toString method to disply just the name of the Contact in combo boxes throughout
     * the program.
     * </p>
     *
     * @return what to display when to string is called for a contact
     */
    @Override
    public String toString() {
        return (contactName);
    }
}
