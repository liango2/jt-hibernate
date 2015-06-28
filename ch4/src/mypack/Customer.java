package mypack;
// Generated 2009-11-6 9:38:07 by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * Represents a single customer.
 *
 * @author LindaSun
 */
public class Customer  implements java.io.Serializable {


     private long id;
     private String name;
     /**
      * When the customer was registered
     */
     private Date registeredTime;
     /**
      * How old is the customer
     */
     private int age;
     private char sex;
     /**
      * Is the customer married
     */
     private boolean married;
     private String description;

    public Customer() {
    }

	
    public Customer(String name, int age) {
        this.name = name;
        this.age = age;
    }
    public Customer(String name, Date registeredTime, int age, char sex, boolean married, String description) {
       this.name = name;
       this.registeredTime = registeredTime;
       this.age = age;
       this.sex = sex;
       this.married = married;
       this.description = description;
    }
   
    public long getId() {
        return this.id;
    }
    
    private void setId(long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    /**       
     *      * When the customer was registered
     */
    public Date getRegisteredTime() {
        return this.registeredTime;
    }
    
    public void setRegisteredTime(Date registeredTime) {
        this.registeredTime = registeredTime;
    }
    /**       
     *      * How old is the customer
     */
    public int getAge() {
        return this.age;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
    public char getSex() {
        return this.sex;
    }
    
    public void setSex(char sex) {
        this.sex = sex;
    }
    /**       
     *      * Is the customer married
     */
    public boolean isMarried() {
        return this.married;
    }
    
    public void setMarried(boolean married) {
        this.married = married;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * toString
     * @return String
     */
     public String toString() {
	  StringBuffer buffer = new StringBuffer();

      buffer.append(getClass().getName()).append("@").append(Integer.toHexString(hashCode())).append(" [");
      buffer.append("name").append("='").append(getName()).append("' ");			
      buffer.append("registeredTime").append("='").append(getRegisteredTime()).append("' ");			
      buffer.append("age").append("='").append(getAge()).append("' ");			
      buffer.append("married").append("='").append(isMarried()).append("' ");			
      buffer.append("description").append("='").append(getDescription()).append("' ");			
      buffer.append("]");
      
      return buffer.toString();
     }



}


