import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;
    @GetMapping("/100001/top/performance")
    public String showPerformance( Model model) {
        var all = performanceService.getAllPerformances();
        model.addAttribute("performances", all);
        return "performance";
    }
}