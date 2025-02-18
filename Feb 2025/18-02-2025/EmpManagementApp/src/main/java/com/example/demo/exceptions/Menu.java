package com.example.demo.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.example.demo.models.service.HashMapUpdater;

public class Menu {
    private static int maxChoice;
    

    public static int validateChoice(int max) {
        maxChoice = max;
        while (true) {
            System.out.println("Enter Choice:- ");
            try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
                @SuppressWarnings("resource")
				int choice =Integer.parseInt(br.readLine());
                
                //Check if there is a CEO or not
                if(!(HashMapUpdater.empMap.containsKey(1)) && choice != 7 && choice != 6) {
                    throw new NoCEOException();
                }
                if((HashMapUpdater.empMap.containsKey(1)) && choice == 7) {
                    throw new CEOAlreadyRegisteredException();
                }
                if(choice < 1 || choice > maxChoice) {
                    throw new InvalidChoiceException();
                }
                
                return choice;
            } catch (InputMismatchException e) {
                System.out.println("Please enter a numeric value only ");
            } catch (NoCEOException e) {
                e.displayMessage();
            } catch (CEOAlreadyRegisteredException e) {
                e.displayMessage();
            } catch (InvalidChoiceException e) {
                e.displayMessage(maxChoice);
            } catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

    }

}
