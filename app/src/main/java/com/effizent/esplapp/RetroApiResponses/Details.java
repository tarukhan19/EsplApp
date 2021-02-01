package com.effizent.esplapp.RetroApiResponses;

public class Details {
String Id,Department,Name,Email,Mobile,TeamLeader,DeviceId,ProfilePicture,Dept_Notifications;

    public String getDept_Notifications() {
        return Dept_Notifications;
    }

    public String getId() {
        return Id;
    }

    public String getDepartment() {
        return Department;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

    public String getMobile() {
        return Mobile;
    }

    public String getTeamLeader() {
        return TeamLeader;
    }

    public String getDeviceId() {
        return DeviceId;
    }

    public String getProfilePicture() {
        return ProfilePicture;
    }

    public Details(String id, String department, String name, String email, String mobile,
                   String teamLeader, String deviceId, String profilePicture,String deptNotifications) {
        Id = id;
        Department = department;
        Name = name;
        Email = email;
        Mobile = mobile;
        TeamLeader = teamLeader;
        DeviceId = deviceId;
        ProfilePicture = profilePicture;
        Dept_Notifications=deptNotifications;
    }
}
