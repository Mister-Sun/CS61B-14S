/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enums;

/**
 *
 * @author sun
 */
public enum AdminType {

    Administritor,
    Guest;

    @Override
    public String toString() {
        switch (this) {
            case Administritor: {
                return "Administritor";
            }
            case Guest: {
                return "Guest";
            }
        }
        return null;
    }

}
