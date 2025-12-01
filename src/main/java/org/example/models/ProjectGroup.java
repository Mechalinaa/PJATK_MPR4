package org.example.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class ProjectGroup {
    @Getter
    @Setter
    public ArrayList<Employee> members;

    public ProjectGroup() {
        this.members = new ArrayList<>();
    }

    public void addMember(Employee employee) {

        if(employee.getProjectGroup() == null){
            for (Employee emp : members) {
                if (emp.getPosition() == employee.getPosition()) {
                    System.out.println("Nie można dodać dwóch pracowników na tym samym stanowisku");
                    return;
                }
            }
            if(members.size() < 5){
                members.add(employee);
                employee.setProjectGroup(this);
            }
            else System.out.println("Maksymalny rozmiar grupy zostal osiagniety");
        }
        else{
            ProjectGroup previousGroup = employee.getProjectGroup();
            previousGroup.getMembers().remove(employee); //usuwam członka z poprzedniej gruoy
            members.add(employee); //dodaję do aktualnej
            employee.setProjectGroup(this); //ustawiam grupę projektową na aktualną
        }




    }


}
