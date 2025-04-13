package dao;

import entity.Admin;
import exception.AdminNotFoundException;
import exception.InvalidInputException;

public interface IAdminService {
    Admin getAdminById(int adminId) throws AdminNotFoundException;

    Admin getAdminByUsername(String username) throws AdminNotFoundException;

    boolean registerAdmin(Admin adminData) throws InvalidInputException;

    boolean updateAdmin(Admin adminData) throws AdminNotFoundException;

    boolean deleteAdmin(int adminId) throws AdminNotFoundException;
}
