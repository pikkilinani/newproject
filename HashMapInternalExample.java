import java.util.*;
//import java.util.Map;
class Student1{
    int id;
    String name;
    Student1(int id,String name){
        this.id=id;
        this.name=name;
    }
    public int hashcode(){
        return id;
    }
    public boolean equals(Object obj){
    if(this==obj)
    return true;
    
    if (obj == null || getClass() != obj.getClass())
    return false;

Student1 S=(Student1)obj;
return id ==S.id&& name.equals(S.name);
}
}
public class HashMapInternalExample {
    public static void main(String[] args) {
        HashMap<Student1,String>map=new HashMap<>();
    Student1 s1 = new Student1(1, "Arjun");
        Student1 s2 = new Student1(2, "Ravi");
        Student1 s3 = new Student1(1, "Arjun");  
        map.put(s1, "First Entry");
        map.put(s2, "Second Entry");
        map.put(s3, "Duplicate Key Entry");
        for (Map.Entry<Student1, String> entry : map.entrySet()) {
            System.out.println(entry.getKey().name + "  " + entry.getValue());
        }
        System.out.println( map.get(s3));
    }
}
    
    
    
    
    


    

