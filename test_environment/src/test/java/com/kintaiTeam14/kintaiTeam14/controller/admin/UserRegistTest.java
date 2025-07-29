package com.kintaiTeam14.kintaiTeam14.controller.admin;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kintaiTeam14.kintaiTeam14.form.UserEditForm;
import com.kintaiTeam14.kintaiTeam14.service.admin.UserEditService;

class UserRegistTest {
	
	private AdminUserEdit adminUserEdit;
    private UserEditForm userEditForm;
    private Model model;
    private UserEditService userEditService;
    private RedirectAttributes ra;
	
	@BeforeEach
    void setUp() {
        adminUserEdit = new AdminUserEdit(userEditService);
        
        userEditForm = new UserEditForm();
        userEditForm.setName("testUser");
        userEditForm.setDepartmentId("D001");
        userEditForm.setEmployeeId("100005");
        
        model = new ExtendedModelMap();
    }
	
	@Test
	@DisplayName("GETリクエストでredirect:/admin/User-managementが返される")
	void testLogin_ReturnsLoginView() {

		String result = adminUserEdit.userEdit(userEditForm,model,ra);

		assertEquals("redirect:/admin/User-management", result);
	}
}
