package com.questionsdata;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class RunQuiz {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/quiz?characterEncoding=utf8",
					"root", "root");
			System.out.println("Welcome...........\n");
			lp: while (true) {

				System.out.println("Choose 1: To solve the questions ");
				System.out.println("Choose 2: To show result of all students");
				System.out.println("Choose 3: To get result of student");
				System.out.println("Choose 4: To exit the program..\n");
				System.out.println("Make your choice");
				String num = sc.next();
				if(num.length()==1) {
				if (Character.isDigit(num.charAt(0))) {
					int n = Integer.parseInt(num);
					switch (n) {
					case 1: {
						l: while (true) {
							System.out.println("Choose 1 : To Confirm -> Solve the Question");
							System.out.println("Choose 2 : Goto previous menu\n");
							System.out.println("Make your choice\n");
							String num1 = sc.next();
							if(num1.length()==1) {
							if (Character.isDigit(num1.charAt(0))) {
								int y = Integer.parseInt(num1);
								switch (y) {
								case 1: {
									int count = 0;
									int stdId = 0;
									String name = null;
									String grade = null;
									int score = 0;

									System.out.println("Enter your id Number");
									stdId = sc.nextInt();
									PreparedStatement pst1 = con.prepareStatement("Select student_id from result");
									ResultSet rs1 = pst1.executeQuery();
									while(rs1.next()) {
										if(stdId == rs1.getInt(1)) {
										count++;
										}
									}
									if(count==0) {
									System.out.println("Enter your name");
									name = sc.next();
									
									QuizMain qz = new QuizMain();

									qz.startQuiz();
									score = qz.countAns;
									grade = qz.grade;
									System.out.println("Student Id = " + stdId);
									System.out.println("Student name = " + name);
									System.out.println("Marks = " + qz.countAns);
									System.out.println("Grade = " + qz.grade);
									System.out.println("\n\n");

									PreparedStatement pst = con.prepareStatement(
											"insert into result(student_id,Student_name,score,grade) values (?,?,?,?)");
									pst.setInt(1, stdId);
									pst.setString(2, name);
									pst.setInt(3, score);
									pst.setString(4, grade);

									pst.execute();
									break;
									}
									else
									{
										System.out.println("Id number = "+stdId+" is already attempted quiz....Please enter a New Student ID..\n");
										
									}
								}
							

								case 2:
									break l;

								default:
									System.out.println("Invalid input..., please make a valid choice\n");
								}
							}
							} else {
								System.out.println("Invalid input..., please make a valid choice\n");
							}
						}
					}
						break;
					case 2: {
						la: while (true) {

							System.out.println("Choose 1 : To Confirm -> get result and list of students");
							System.out.println("Choose 2 : Goto previous menu\n");
							System.out.println("Make your choice\n");
							String num2 = sc.next();
							if(num2.length()==1) {
							if (Character.isDigit(num2.charAt(0))) {
								int x = Integer.parseInt(num2);
								switch (x) {
								case 1:
									System.out.println("RESULT AND STUDENT LIST : \n");

									PreparedStatement pst_result = con.prepareStatement(
											"Select student_id,Student_name,score,grade from result order by score desc");
									ResultSet rs = pst_result.executeQuery();
									while (rs.next()) {
										{
											System.out.print("Id = " + rs.getString(1));
											System.out.print("\t Name = " + rs.getString(2));
											System.out.print("\t Score = " + rs.getString(3));
											System.out.print("\t Grade = " + rs.getString(4));
										}
										System.out.println();
									}
									System.out.println("\n\n");
									break la;

								case 2:
									break la;

								default:
									System.out.println("Invalid input..., please make a valid choice\n");
								}
							}
							} else {
								System.out.println("Invalid input..., please make a valid choice\n");
							}
						}
					}
						break;
					case 3: {

						lc: while (true) {
							System.out.println("Choose 1 : To Confirm -> get result of a student");
							System.out.println("Choose 2 : Goto previous menu\n");
							System.out.println("Make your choice");
							String num3 = sc.next();
							if(num3.length()==1) {
							if (Character.isDigit(num3.charAt(0))) {
								int a = Integer.parseInt(num3);
								switch (a) {

								case 1:
									System.out.println("Enter Student id ");
									int count = 0;
									int id = sc.nextInt();
									PreparedStatement pst2 = con
											.prepareStatement("Select * from result where student_id = ?");
									pst2.setInt(1, id);
									ResultSet rs = pst2.executeQuery();
									while(rs.next()) {
										if(id==rs.getInt(1)) {
											count++;
										}
									}
									if(count>0) {
									while (rs.next()) {

										System.out.print("Id = " + rs.getInt(1));
										System.out.print("\t Name = " + rs.getString(2));
										System.out.print("\t Score = " + rs.getString(3));
										System.out.print("\t Grade = " + rs.getString(4));
										}
									}
									else {
										System.out.println("There is no user of Id number "+id+". You have entered wrong ID");
									}
									System.out.println("\n\n");
									break;

								case 2:
									break lc;

								default:
									System.out.println("Invalid input..., please make a valid choice\n");
								}
							}
							
							} else {
								System.out.println("Invalid input..., please make a valid choice\n");
							}
						}
						break;
					}

					case 4:
						System.out.println("\nThank you........................See You Later");
						break lp;

					default:
						System.out.println("Invalid input...., Please make a valid choice\n");
					}
				}
				}

				else {
					System.out.println("Invalid input.......Please make valid choice\n");
				}
				
			}

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

	}

}
