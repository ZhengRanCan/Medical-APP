package com.example.myapplication.tonguedetection;
import java.io.Serializable;

//noinspection AndroidLintInstantiatable
public class UserInfo implements Serializable {
//input1-5分别对应病人编号，姓名，性别，就诊时间，就诊科室
    private String input1 ;
    private String input2 ;
    private String input3;
    private String input4;
    private String input5;

    UserInfo(){}

    public UserInfo(String input1, String input2, String input3, String input4, String input5) {
        this.input1 = input1;
        this.input2 = input2;
        this.input3= input3;
        this.input4 = input4;
        this.input5 = input5;
    }

    public String getInput1(){return input1;}
    public void setInput1(String input1){this.input1 = input1;}

    public String getInput2(){return input2;}
    public void setInput2(String input2){this.input2 = input2;}

    public String getInput3(){return input3;}
    public void setInput3(String input3){this.input3 = input3;}

    public String getInput4(){return input4;}
    public void setInput4(String input4){this.input4 = input4;}

    public String getInput5(){return input5;}
    public void setInput5(String input5){this.input5 = input5;}

}