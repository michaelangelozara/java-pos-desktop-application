package department;

import org.POS.backend.department.AddDepartmentRequestDto;
import org.POS.backend.department.DepartmentService;
import org.POS.backend.department.DepartmentStatus;
import org.POS.backend.department.UpdateDepartmentRequestDto;
import org.junit.jupiter.api.Test;

public class DepartmentTest {


    @Test
    void add(){
        DepartmentService departmentService = new DepartmentService();
        AddDepartmentRequestDto dto = new AddDepartmentRequestDto(
                "Human Resource",
                DepartmentStatus.ACTIVE,
                "No note"
        );

        departmentService.add(dto);
    }

    @Test
    void update(){
        DepartmentService departmentService = new DepartmentService();
        UpdateDepartmentRequestDto dto = new UpdateDepartmentRequestDto(1,"Information Kalokohan", DepartmentStatus.ACTIVE, "No Tae");
        departmentService.update(dto);
    }

    @Test
    void delete(){
        DepartmentService departmentService = new DepartmentService();
        departmentService.delete(1);
    }

    @Test
    void getValidDepartment(){
        DepartmentService departmentService = new DepartmentService();
        System.out.println(departmentService.getDepartmentById(1));
    }

    @Test
    void getAllValidDepartment(){
        DepartmentService departmentService = new DepartmentService();
        departmentService.getAllValidDepartment().forEach(d -> {
            System.out.println(d.name());
        });
    }
}
