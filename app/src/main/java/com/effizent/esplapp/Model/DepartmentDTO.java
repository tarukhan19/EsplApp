package com.effizent.esplapp.Model;

public class DepartmentDTO {
    String DeptName;
    private boolean isSelected = false;

    public DepartmentDTO() {

    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public boolean isSelected() {
        return isSelected;
    }
}
