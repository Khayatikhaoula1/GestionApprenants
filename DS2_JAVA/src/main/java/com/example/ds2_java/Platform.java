package com.example.ds2_java;

import java.util.ArrayList;
import java.util.List;

public class Platform {
    private List<User> userList = new ArrayList<>();


    public void addUser(User user) throws InvalidEmailException{
       if(!isValidEmail(user.getEmail(),userList))
           throw new InvalidEmailException();
       userList.add(user);

    }

    public void updateUser(User user,int index) throws InvalidEmailException, InvalidNameException {


        if (!User.isValidName(user.getFirstName()) || !User.isValidName(user.getLastName())) {
            throw new InvalidNameException();
        } int i = 0;



        if (index >=1 && index <= userList.size()) {
            User user1 = getUserById(index);
            user1.setEmail("justforchanging@gmail.com");
            if(!isValidEmail(user.getEmail(),userList))
                throw new InvalidEmailException();
            for (User user2 : userList) {
                i++;
                if (user2.getId() == index) {
                    break;
                }
            }
            userList.set(i-1, user);
        }

    }

    public void deleteUser(User user) {
        userList.remove(user);
    }

    public User getUserById(int id) {
        for (User user : userList) {
            if (user.getId() == id) {
                return user;
            }
        }
        return null;
    }

    public List<User> getUserList() {
        return userList;
    }

    public static boolean isValidEmail(String email, List<User> userList) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        String regex = "^[\\w-\\.]+@(yahoo\\.com|hotmail\\.com|gmail\\.com)$";
        if (!email.matches(regex)) {
            return false;
        }
        for (User user : userList) {
            if (user.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    public static int countUsersByCourseName(List<User> users, String courseName) {
        int count = 0;
        for (User user : users) {
            if (user.getCourse().contains(courseName)) {
                count++;
            }
        }
        return count;
    }

}

