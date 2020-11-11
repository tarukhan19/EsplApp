package com.effizent.esplapp.RetroApiResponses;

import com.effizent.esplapp.Model.DepartmentDTO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class LoadDepartmentResult {
    @SerializedName("Code")
    @Expose
    String Code;

    @SerializedName("Status")
    @Expose
    String Status;

    @SerializedName("Dept")
    @Expose
    private ArrayList<DepartmentDTO> departmentDTOArrayList;

    public String getCode() {
        return Code;
    }

    public String getStatus() {
        return Status;
    }

    public ArrayList<DepartmentDTO> getDepartmentDTOArrayList() {
        return departmentDTOArrayList;
    }


}
