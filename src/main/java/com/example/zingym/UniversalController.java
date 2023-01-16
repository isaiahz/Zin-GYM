package com.example.zingym;

import com.example.zingym.admin.Admin;
import com.example.zingym.trainee.CustomTraineeDetails;
import com.example.zingym.trainee.Trainee;
import com.example.zingym.trainee.TraineeRepository;
import com.example.zingym.trainer.CustomTrainerDetails;
import com.example.zingym.trainer.Trainer;
import com.example.zingym.trainer.TrainerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class UniversalController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClassesRepository classesRepository;

    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private TrainerRepository trainerRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private TraineeRepository traineeRepository;

    @Autowired
    private DeleteProfileRequestsRepository deleteProfileRequestsRepository;

    @GetMapping("")
    public String viewHomePage(@ModelAttribute("trainee") Trainee trainee) {
        return "trainee/default_page";
    }

    @GetMapping("/trainee_signup")
    public String showTraineeSignUpForm(Model model) {
        model.addAttribute("trainee", new Trainee());
        return "trainee/trainee_signup_form";
    }

    @GetMapping("/trainer_signup")
    public String showTrainerSignUpForm(Model model) {
        model.addAttribute("trainer", new Trainer());
        return "trainer/trainer_signup_form";
    }

    @GetMapping("/trainer/login")
    public String viewTrainerLoginPage(@ModelAttribute("trainer") Trainer trainer) {
//        model.addAttribute("message", "You have successfully logged out...");
        return "trainer/trainer_login";
    }

    @GetMapping("/trainee/login")
    public String viewTraineeLoginPage(@ModelAttribute("trainee") Trainee trainee, Model model) {

//        model.addAttribute("message", "You have successfully logged out...");

        return "trainee/default_page";
    }

    @GetMapping("/admin/login")
    public String viewAdminLoginPage(@ModelAttribute("admin") Admin admin, HttpServletRequest request) {
//        request.getSession().invalidate();

        return "admin/admin_login";
    }

    @GetMapping("/trainee")
    public String viewTraineeHomePage(@ModelAttribute("bookClass") Classes classes, Model model, Trainee trainee, Authentication authentication) {

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);
//		System.out.println("This is the name " + id);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        return "trainee/trainee";
    }

    @GetMapping("/trainer")
    public String trainerDashBoard(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//        System.out.println("This is the ID " + id);
        String name = customUserDetails.getFullName();

        String trainerIDD = String.valueOf(id);

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findByTrainerID(trainerIDD);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        return "trainer/trainer";
    }


    @GetMapping("/trainee/logout")
    public String logout(HttpServletRequest request, Model model, @ModelAttribute("trainee") Trainee trainee) {
        request.getSession().invalidate();

//        model.addAttribute("message", "You have successfully logged out...");

        return "redirect:/trainee/default_page";
    }

    @GetMapping("/trainer/logout")
    public String logoutTrainer(HttpServletRequest request, Model model, @ModelAttribute("trainee") Trainee trainee) {
        request.getSession().invalidate();

//        model.addAttribute("message", "You have successfully logged out...");

        return "redirect:/trainer/trainer_login";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(HttpServletRequest request, Model model, @ModelAttribute("trainee") Trainee trainee) {
        request.getSession().invalidate();

//        model.addAttribute("message", "You have successfully logged out...");

        return "redirect:/admin/admin_login";
    }

    @PostMapping("/trainee_process_register")
    public String processTraineeSignUpForm(@ModelAttribute("trainee") Trainee trainee, Model model) {
        model.addAttribute("message", "Successfully Registered, Please Login...");
        userService.registerTrainee(trainee);
        return "trainee/default_page";
    }

    @PostMapping("/trainer/trainer_process_register")
    public String TrainerProcessRegister(@ModelAttribute("trainer") Trainer trainer, Model model,
                                         Bookings bookings,
                                         DeleteProfileRequests deleteProfileRequests,
                                         Authentication authentication, @RequestParam String email,
                                         @RequestParam String firstName, @RequestParam String lastName) {
        model.addAttribute("message", "Successfully Registered, Please Login...");
//
////        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
////        Long id = customUserDetails.getID();
////
////        String email = customUserDetails.getEmail();

        String fullName = firstName + " " + lastName;
//
        deleteProfileRequests.setEmail(email);
        deleteProfileRequests.setTrainerName(fullName);
//        deleteProfileRequests.setTrainerID(String.valueOf(9L));
        deleteProfileRequests.setReason("N/A");
        deleteProfileRequestsRepository.save(deleteProfileRequests);
//
//        deleteProfileRequests = "N/A";

        trainer.setAccountStatus("InActive");
//		trainer.setDeleteProfileRequests(deleteProfileRequests);
        trainerRepository.save(trainer);

        deleteProfileRequests = deleteProfileRequestsRepository.findByEmailAndTrainerName(email, fullName);
        trainer.setDeleteProfileRequests(deleteProfileRequests);
        trainerRepository.save(trainer);


        trainer = trainerRepository.findByEmail(email);
        String trainerID = (trainer.getId().toString());
        deleteProfileRequests.setTrainerID(trainerID);
        deleteProfileRequestsRepository.save(deleteProfileRequests);
//        userService.registerTrainer(trainer);

        return "trainer/trainer_login";
    }

    @RequestMapping(value = "/trainee/deleteProfileTrainee", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteTraineeProfile(Model model, Bookings bookings, Authentication authentication) {

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
        traineeRepository.deleteByUserId((id));
//		System.out.println("this is the name " + name);
        return "redirect:/trainee/profileTraineeDeleteRefresh";
    }

    @GetMapping("/trainee/profileTraineeDeleteRefresh")
    public String profileTraineeDeleteRefresh(Trainee user, Model model) {
        model.addAttribute("user", new Trainee());
        model.addAttribute("message", "Account Deleted Successfully...");
        return "trainee/default_page";
    }

    @RequestMapping(value = "/trainer/deleteProfileTrainer", method = {RequestMethod.PUT, RequestMethod.POST})
    public String deleteTrainerProfile(@RequestParam String reason, Model model, Trainer trainer, Bookings bookings,
                                       DeleteProfileRequests deleteProfileRequests,
                                       Authentication authentication) {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
        String email = customUserDetails.getEmail();



//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
//        trainerRepository.deleteByUserId((id));
//		System.out.println("this is the name " + name);
        String fullNames = customUserDetails.getFullName();
        deleteProfileRequests = deleteProfileRequestsRepository.findByEmailAndTrainerName(email, fullNames);

        if (deleteProfileRequests.getReason().equals("N/A")) {
            String fullName = customUserDetails.getFullName();

            deleteProfileRequests = deleteProfileRequestsRepository.findByEmailAndTrainerName(email, fullName);

//            deleteProfileRequests.setEmail(email);
//            deleteProfileRequests.setTrainerName(fullName);
//            deleteProfileRequests.setTrainerID(String.valueOf(id));
            deleteProfileRequests.setReason(reason);
            deleteProfileRequestsRepository.save(deleteProfileRequests);

//            deleteProfileRequests = deleteProfileRequestsRepository.findByTrainerNameAndTrainerID(email, String.valueOf(id));
//
            trainer = trainerRepository.findByEmail(email);
            trainer.setAccountStatus("Active (Account Deletion Requested)");
//            trainer.setDeleteProfileRequests(deleteProfileRequests);
            trainerRepository.save(trainer);
            return "redirect:/trainer/profileTrainerDeleteRefresh";
        } else {
            model.addAttribute("trainer", trainerRepository.findById(id));

//        ArrayList<String> trainingStyle = new ArrayList<String>();
//        trainingStyle.add("Boot Camp");
//        trainingStyle.add("Motivational");
//        model.addAttribute("trainingStyle", trainingStyle);

            model.addAttribute("classes", new Classes());
            List<Activity> listActivities = activityRepository.findAll();
            model.addAttribute("listActivities", listActivities);
            model.addAttribute("message", "Your request is already pending approval");
            return "trainer/view_profile_trainer";
        }
    }

    @GetMapping("/trainer/profileTrainerDeleteRefresh")
    public String profileTrainerDeleteRefresh(Trainer trainer, Model model, Authentication authentication) {
        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//        System.out.println("This is the ID " + id);
        String name = customUserDetails.getFullName();

        String trainerIDD = String.valueOf(id);

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findByTrainerID(trainerIDD);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "Delete Request Sent...");
        return "trainer/trainer";
    }


    @GetMapping("/admin")
    public String listUsersForAdmin(Model model) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        model.addAttribute("activity", new Activity());

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        return "admin/admin";
    }

    @PostMapping("/admin/add_activity")
    public String AddActivity(Activity activity, Model model) {
        userService.activities(activity);
        return "redirect:/activityPages";
    }

    @PostMapping("/admin/delete_activity")
    public String deleteActivity(@RequestParam Long id, Model model) {
        activityRepository.deleteById(id);
        return "redirect:/getAdminPage";
    }

    @GetMapping("/getAdminPage")
    public String getAdminPage(Model model) {
        model.addAttribute("activity", new Activity());

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);
        model.addAttribute("message", "Activity Deleted Successfully...");
        return "admin/admin";
    }

    @GetMapping("/activityPages")
    public String classAddActivity(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        model.addAttribute("activity", new Activity());

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("message", "Activity Added Successfully...");

        return "admin/admin";
    }

    @GetMapping("/trainer/class_add")
    public String getAddClassPage(Model model, Authentication authentication) {
        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

//        List<User> listUsers = userRepo.findAll();

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);
//        model.addAttribute("user", listUsers);

//		CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
//		Long id = customUserDetails.getID();
//		model.addAttribute("userID", id);


        return "trainer/add_class";
    }

    @PostMapping("/trainer/addd_class")
    public String AddClass(@RequestParam String firstName, Classes classes, Model model, @RequestParam String className, Authentication authentication) {
        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//		System.out.println(id+ " IS THE ID");
//
//		if (userRepo.findById(id)== null){
//			System.out.println("NO SUCH ID IN HERE");
//		}

//
//		else {
//			System.out.println("THE ID IS THERE");
//		}
//		user = userRepo.findById(id);

        if (classesRepository.findByClassName(className) == null){
            classes.setTrainerId(id);
            classes.setTrainerName(firstName);
            classesRepository.save(classes);
            userService.classes(classes);
//		classes.setUser(String.valueOf(id));
//		classesRepository.save(classes);
//		userService.classes2(classes, user);
            return "redirect:/trainer/trainerPage";
        } else if (classesRepository.findByClassName(className) != null) {
            model.addAttribute("classes", new Classes());
            List<Activity> listActivities = activityRepository.findAll();
            model.addAttribute("listActivities", listActivities);

//        List<User> listUsers = userRepo.findAll();

            String fullName = customUserDetails.getFullName();
            model.addAttribute("fullname", fullName);
            model.addAttribute("message", "Class Name Already Exists");
            return "trainer/add_class";
        }
        return "trainer/trainer";

    }


    @GetMapping("/trainer/trainerPage")
    public String classAddd(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        List<Trainee> listUsers = userService.listTrainee();
        model.addAttribute("listUsers", listUsers);

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//		String name = customUserDetails.getName();
        String name = customUserDetails.getFullName();
        String trainerIDD = String.valueOf(id);

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findByTrainerID(trainerIDD);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "Class Added Successfully...");

        return "trainer/trainer";
    }

    @RequestMapping(value = "/trainer/deleteClass", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteClass(@RequestParam Integer id,
                              Authentication authentication, @RequestParam String className, Model model, Bookings bookings, HttpServletRequest request) {

        CustomTrainerDetails customTrainerDetails = (CustomTrainerDetails) authentication.getPrincipal();
        String trainerId = customTrainerDetails.getID().toString();
//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
        bookingsRepository.deleteByClassNameAndClassID(className, trainerId);
        classesRepository.deleteById((id));
//		System.out.println("this is the name " + name);
        return "redirect:/trainer/trainerRefresh";
    }

    @GetMapping("/trainer/trainerRefresh")
    public String trainerRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
        String name = customUserDetails.getFullName();

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());
        model.addAttribute("message", "Successfully deleted class...");
        return "trainer/trainer";
    }

    @GetMapping("/trainer/view_profile_trainer")
    public String showTrainerProfile(Model model, Authentication authentication) {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
        model.addAttribute("trainer", trainerRepository.findById(id));

//        ArrayList<String> trainingStyle = new ArrayList<String>();
//        trainingStyle.add("Boot Camp");
//        trainingStyle.add("Motivational");
//        model.addAttribute("trainingStyle", trainingStyle);

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);
        return "/trainer/view_profile_trainer";
    }

    @GetMapping("/trainee/view_profile_trainee")
    public String showTraineeProfile(Model model, Authentication authentication) {

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
        model.addAttribute("trainee", traineeRepository.findById(id));

//        ArrayList<String> trainingStyle = new ArrayList<String>();
//        trainingStyle.add("Boot Camp");
//        trainingStyle.add("Motivational");
//        model.addAttribute("trainingStyle", trainingStyle);


        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);
        return "trainee/view_profile_trainee";
    }

    @GetMapping("/trainer/trainerProfileEdit")
    public String showEditFormTrainer(Model model, Authentication authentication) {

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
        model.addAttribute("trainer", trainerRepository.findById(id));
        return "trainer/trainer_profile_edit";
    }

    @RequestMapping(value = "/trainer/saveTrainerEdit", method = {RequestMethod.PUT, RequestMethod.POST})
    public String saveTrainerEdit(Trainer trainer, HttpServletRequest request) {
//        user.setRawPassword(password);
//        user.setAccountStatus("Active");
//        userRepo.save(user);
//        trainer.setAccountStatus("Active");
//        trainerRepository.save(trainer);
        userService.registerTrainerEdit(trainer);


////        String data1 = request.getParameter("id");
////		System.out.println("This is the id " + data1);
//        String data2 = request.getParameter("email");
//        String data3 = request.getParameter("password");
//        String data4 = request.getParameter("firstName");
//        String data5 = request.getParameter("lastName");
//        String data6 = request.getParameter("gender");
//        String data7 = request.getParameter("phoneNumber");
//        String data8 = request.getParameter("experienceLevel");
//        String data9 = request.getParameter("trainingStyle");
//        String data10 = request.getParameter("trainingActivity");
////
////        trainer =trainerRepository.findByName(Long.valueOf(data1));
////
////        Long userId = Long.valueOf(data1);
////
////        Long id = trainer.getId();
////
////        trainer.setId(userId);
//        trainer.setEmail(data2);
//        trainer.setPassword(data3);
//        trainer.setFirstName(data4);
//        trainer.setLastName(data5);
//        trainer.setGender(data6);
//        trainer.setPhoneNumber(data7);
//        trainer.setTrainingStyle(data9);
//        trainer.setTrainingActivity(data10);
//        trainer.setAccountStatus("Active");
//        trainerRepository.save(trainer);

//        System.out.println("This is the style " + data9);
//
//        System.out.println("This is the activity " + data10);

//         <div class="profileFormButton">
//          <a th:href="@{/trainer/trainerProfileEdit}"><button class="btn btn-primary">Edit</button></a>
//          <button type="button" class="btn btn-primary" data-toggle="modal"  data-target="#modalDeleteUser">Delete Profile</button>
//      </div>

        return "redirect:/trainer/trainerPage2";
    }

    @GetMapping("/trainer/trainerPage2")
    public String adminPageOpennn(Model model) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);
        model.addAttribute("message", "Updated Successfully...");
        return "trainer/trainer";
    }

    @RequestMapping(value = "/trainee/saveTraineeEdit", method = {RequestMethod.PUT, RequestMethod.POST})
    public String saveTraineeEdit(Trainee trainee, HttpServletRequest request) {
//        user.setRawPassword(password);
//        user.setAccountStatus("Active");
//        userRepo.save(user);
//        trainer.setAccountStatus("Active");
//        trainerRepository.save(trainer);
        userService.registerTraineeEdit(trainee);


////        String data1 = request.getParameter("id");
////		System.out.println("This is the id " + data1);
//        String data2 = request.getParameter("email");
//        String data3 = request.getParameter("password");
//        String data4 = request.getParameter("firstName");
//        String data5 = request.getParameter("lastName");
//        String data6 = request.getParameter("gender");
//        String data7 = request.getParameter("phoneNumber");
//        String data8 = request.getParameter("experienceLevel");
//        String data9 = request.getParameter("trainingStyle");
//        String data10 = request.getParameter("trainingActivity");
////
////        trainer =trainerRepository.findByName(Long.valueOf(data1));
////
////        Long userId = Long.valueOf(data1);
////
////        Long id = trainer.getId();
////
////        trainer.setId(userId);
//        trainee.setEmail(data2);
//        trainee.setPassword(data3);
//        trainee.setFirstName(data4);
//        trainee.setLastName(data5);
//        trainee.setGender(data6);
//        trainee.setPhoneNumber(data7);
//        trainee.setTrainingStyle(data9);
//        trainee.setTrainingActivity(data10);
//        trainee.setAccountStatus("Active");
//        traineeRepository.save(trainee);

//         <div class="profileFormButton">
//          <a th:href="@{/trainer/trainerProfileEdit}"><button class="btn btn-primary">Edit</button></a>
//          <button type="button" class="btn btn-primary" data-toggle="modal"  data-target="#modalDeleteUser">Delete Profile</button>
//      </div>

        return "redirect:/trainee/traineePage2";
    }


    @GetMapping("/trainee/traineePage2")
    public String adminPageOpennnh(Model model, Authentication authentication) {

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);
//		System.out.println("This is the name " + id);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);
        model.addAttribute("message", "Updated Successfully...");
        return "trainee/trainee";
    }

    @RequestMapping(value = "/trainer/updateTrainer", method = {RequestMethod.PUT, RequestMethod.POST})
    public String updateTrainer(@RequestParam Long id, @RequestParam String password, Trainer trainer, HttpServletRequest request) {
//        user.setRawPassword(password);
        trainerRepository.save(trainer);
//        userService.registerTrainerTest(trainer);
////		user = userRepo.findById(id);
////		String data36 = request.getParameter("password");
////		user.setRawPassword(data36);
////		userRepo.save(user);
//
//
//        String data1 = request.getParameter("id");
////		System.out.println("This is the id " + data1);
//        String data2 = request.getParameter("email");
//        String data3 = request.getParameter("password");
//        String data4 = request.getParameter("firstName");
//        String data5 = request.getParameter("lastName");
//        String data6 = request.getParameter("gender");
//        String data7 = request.getParameter("phoneNumber");
////		String data8 = request.getParameter("experienceLevel");
//        String data9 = request.getParameter("trainingStyle");
//        String data10 = request.getParameter("trainingActivity");
//
//        trainer =trainerRepository.findByName(Long.valueOf(data1));
//
//        Long userId = Long.valueOf(data1);
//
//        Long idd = trainer.getId();
//
//        trainer.setId(idd);
//        trainer.setUser(user);
//        trainer.setEmail(data2);
//        trainer.setPassword(data3);
//        trainer.setFirstName(data4);
//        trainer.setLastName(data5);
//        trainer.setGender(data6);
//        trainer.setPhoneNumber(data7);
//        trainer.setTrainingStyle(data9);
//        trainer.setTrainingActivity(data10);
//        trainerRepository.save(trainer);

        return "redirect:/trainer/adminPageOpenAgain";
    }

    @GetMapping("/trainer/adminPageOpenAgain")
    public String adminPageOpenAgain(Model model) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("message", "Updated Successfully...");
        return "admin/admin";
    }

    @RequestMapping(value = "/trainee/updateTrainee", method = {RequestMethod.PUT, RequestMethod.POST})
    public String updateTrainee(@RequestParam Long id, @RequestParam String password, Trainee trainee, HttpServletRequest request) {
//        user.setRawPassword(password);
        traineeRepository.save(trainee);
//        userService.registerTrainerTest(trainer);
////		user = userRepo.findById(id);
////		String data36 = request.getParameter("password");
////		user.setRawPassword(data36);
////		userRepo.save(user);
//
//
//        String data1 = request.getParameter("id");
////		System.out.println("This is the id " + data1);
//        String data2 = request.getParameter("email");
//        String data3 = request.getParameter("password");
//        String data4 = request.getParameter("firstName");
//        String data5 = request.getParameter("lastName");
//        String data6 = request.getParameter("gender");
//        String data7 = request.getParameter("phoneNumber");
////		String data8 = request.getParameter("experienceLevel");
//        String data9 = request.getParameter("trainingStyle");
//        String data10 = request.getParameter("trainingActivity");
//
//        trainer =trainerRepository.findByName(Long.valueOf(data1));
//
//        Long userId = Long.valueOf(data1);
//
//        Long idd = trainer.getId();
//
//        trainer.setId(idd);
//        trainer.setUser(user);
//        trainer.setEmail(data2);
//        trainer.setPassword(data3);
//        trainer.setFirstName(data4);
//        trainer.setLastName(data5);
//        trainer.setGender(data6);
//        trainer.setPhoneNumber(data7);
//        trainer.setTrainingStyle(data9);
//        trainer.setTrainingActivity(data10);
//        trainerRepository.save(trainer);

        return "redirect:/trainee/adminPageOpenAgain3";
    }

    @GetMapping("/trainee/adminPageOpenAgain3")
    public String adminPageOpenAgain3(Model model) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("message", "Updated Successfully...");
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/deleteTrainer", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteTrainer(@RequestParam Long id, Model model, DeleteProfileRequests deleteProfileRequests, Bookings bookings,
                                Authentication authentication, HttpServletRequest request, Classes classes,
                                @RequestParam String email, @RequestParam String firstName,
                                @RequestParam String lastName) {

        String fullName = firstName + " " + lastName;
//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");

        deleteProfileRequests = deleteProfileRequestsRepository.findByEmailAndTrainerName(email, fullName);

        Long idRequest = Long.valueOf(deleteProfileRequests.getId().toString());

        Integer trainerIdBookings = Integer.valueOf(deleteProfileRequests.getTrainerID());

//        bookings = bookingsRepository.findByTrainerIDAndTrainerName(String.valueOf(trainerIdBookings), fullName);
//        Integer idBookings = Integer.valueOf(bookings.getId().toString());
        bookingsRepository.deleteByTrainerNameANdTrainerId(fullName, String.valueOf(id));


//        classes = classesRepository.findByTrainerIDAndTrainerName(trainerIdBookings, fullName);
//        Integer idClasses = Integer.valueOf(classes.getId().toString());
        classesRepository.deleteByTrainerNameANdTrainerId(fullName, id);


        trainerRepository.deleteByUserId((id));
        deleteProfileRequestsRepository.deleteById(idRequest);
//		System.out.println("this is the id " + idRequest);
        return "redirect:/trainerDeleteRefresh";
    }

    @GetMapping("/trainerDeleteRefresh")
    public String trainerDeleteRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);



        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());

        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("message", "Account Deleted Successfully...");
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/deleteTrainee", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteTrainee(@RequestParam Long id, Model model, Bookings bookings, HttpServletRequest request) {

//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
        traineeRepository.deleteByUserId((id));
//		System.out.println("this is the name " + name);
        return "redirect:/traineeDeleteRefresh";
    }

    @GetMapping("/traineeDeleteRefresh")
    public String traineeDeleteRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());

        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("message", "Account Deleted Successfully...");
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/accountStatusTrainer", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String statusTrainer(@RequestParam Long id, @RequestParam String accountStatus, Model model,
                                Trainer trainer, Bookings bookings, HttpServletRequest request) {

//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
//		System.out.println("this is the id " + id);


        trainer = trainerRepository.findById(id);
//		user.setAccountStatus("InActive");
//		userRepo.save(user);
//
//		System.out.println("this is the status " + accountStatus);

        if (accountStatus.equals("Active") || accountStatus.equals("Active (Account Deletion Requested)")) {
            trainer.setAccountStatus("InActive");
            trainerRepository.save(trainer);
        } else if (accountStatus.equals("InActive")) {
            trainer.setAccountStatus("Active");
            trainerRepository.save(trainer);
        }

//		trainerRepository.deleteByUserId((id));
//		userRepo.deleteById((id));
//		System.out.println("this is the name " + name);
        return "redirect:/trainerStatusRefresh";
    }

    @GetMapping("/trainerStatusRefresh")
    public String trainerStatusRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        model.addAttribute("activity", new Activity());

        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("message", "Successfully Updated...");
        return "admin/admin";
    }

    @RequestMapping(value = "/admin/accountStatusTrainee", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String statusTrainee(@RequestParam Long id, @RequestParam String accountStatus, Model model,
                                Trainee trainee, Bookings bookings, HttpServletRequest request) {

//				System.out.println("this is the id " + id);
//		String data2 = request.getParameter("className");
//		System.out.println("this is the id " + id);


        trainee = traineeRepository.findById(id);
//		user.setAccountStatus("InActive");
//		userRepo.save(user);
//
//		System.out.println("this is the status " + accountStatus);

        if (accountStatus.equals("Active")) {
            trainee.setAccountStatus("InActive");
            traineeRepository.save(trainee);
        } else if (accountStatus.equals("InActive")) {
            trainee.setAccountStatus("Active");
            traineeRepository.save(trainee);
        }

//		trainerRepository.deleteByUserId((id));
//		userRepo.deleteById((id));
//		System.out.println("this is the name " + name);
        return "redirect:/traineeStatusRefresh";
    }

    @GetMapping("/traineeStatusRefresh")
    public String traineeStatusRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        model.addAttribute("activity", new Activity());

        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("message", "Successfully Updated...");
        return "admin/admin";
    }

    @PostMapping("/trainee/addBooking")
    public String addBooking(HttpServletRequest request, Model model, Bookings bookings, Authentication authentication) {

//        String classId = request.getParameter("id");
        String className = request.getParameter("className");
        String trainerName = request.getParameter("trainerName");
        String trainerId = request.getParameter("trainerId");
        String traineeName = request.getParameter("traineeName");
        String classActivity = request.getParameter("classActivity");
        String date = request.getParameter("date");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");

        CustomTraineeDetails customUserDetail = (CustomTraineeDetails) authentication.getPrincipal();

        Long idd = customUserDetail.getID();

        String id1 = String.valueOf(idd);

        Classes classes = classesRepository.findByNameAndId(className);
        String classId = String.valueOf(classes.getId());

        int spots = classes.getNumberOfSpots();

        String time = startTime + " - " + endTime;


        if (spots >= 1 && bookingsRepository.findByTraineeIDAndClassNameAndTrainerName(id1, className, trainerName, traineeName, classActivity, trainerId, date, time, classId) == null) {

            int spots2 = spots - 1;
            classes.setNumberOfSpots(spots2);

            CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

            Long id = customUserDetails.getID();

            bookings.setTraineeId(String.valueOf((id)));
            bookings.setClassName(className);
            bookings.setTrainerName(trainerName);
            bookings.setTraineeName(traineeName);
            bookings.setClassActivity(classActivity);
            bookings.setTrainerId(trainerId);
            bookings.setClassId(classId);
            bookings.setDate(date);
            bookings.setTime(startTime + " - " + endTime);

            bookingsRepository.save(bookings);

            return "redirect:/trainee/traineeDirect2";
        } else if (spots >= 1 && bookingsRepository.findByTraineeIDAndClassNameAndTrainerName(id1, className, trainerName, traineeName, classActivity, trainerId, date, time, classId) != null) {
            return "redirect:/trainee/traineeDirect3";
        } else if (spots == 0) {
            return "redirect:/trainee/traineeDirect";
        }

        return "trainee/trainee";
    }

    @GetMapping("/trainee/traineeDirect")
    public String traineeDirect(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("user", new Trainee());

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();
//		System.out.println("This is the name " + id);

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "Sorry, this class is full");

        return "trainee/trainee";
    }

    @GetMapping("/trainee/traineeDirect2")
    public String traineeDirect2(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("user", new Trainee());

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();
//		System.out.println("This is the name " + id);

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "Successfully Booked....");

        return "trainee/trainee";
    }

    @GetMapping("/trainee/traineeDirect3")
    public String traineeDirect3(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("user", new Trainee());

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();
//		System.out.println("This is the name " + id);

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "You have already booked this class....");

        return "trainee/trainee";
    }

    @RequestMapping(value = "/trainee/deleteBooking", method = {RequestMethod.DELETE, RequestMethod.GET})
    public String deleteBooking(@RequestParam Integer id, @RequestParam Integer class_id, @RequestParam String className, Model model, Bookings bookings, Classes classes, HttpServletRequest request) {
        classes = classesRepository.findByNameAndId(className);
        classes.setNumberOfSpots(classes.getNumberOfSpots() + 1);
        bookingsRepository.deleteById((id));
//        return "redirect:/trainee/traineeRefresh";

        String section = "#bookclass-class-section";

//        return "admin/admin";
//        redirectAttributes.addFlashAttribute("accountStatus", accountStatus);
        return "redirect:/trainee/traineeRefresh" + section;
    }

    @GetMapping("/trainee/traineeRefresh")
    public String traineeRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("user", new Trainee());

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();
//		System.out.println("This is the name " + id);

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        model.addAttribute("message", "Successfully deleted booking...");

        return "trainee/trainee";
    }

    @PostMapping("/trainee/filter_search")
    public String filterSearch(@ModelAttribute("bookClass") Classes classes, Model model,
                               Authentication authentication, @RequestParam String style, @RequestParam String activity,
                               @RequestParam String classtype) {
        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findByStyleAndActivityAndClassType(style, activity, classtype);
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("user", new Trainee());

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();
//		System.out.println("This is the name " + id);

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

//        System.out.println("This is the style" + style);
//        System.out.println("This is the activity" + activity);
//        System.out.println("This is the classtype" + classtype);

        return "trainee/trainee";
    }

    @PostMapping("/trainer/filter_search")
    public String filterSearchTrainer(@ModelAttribute("bookClass") Classes classes, Model model,
                               Authentication authentication, @RequestParam String className) {
        //        List<Trainee> listUsers = userService.listTrainee();
//        model.addAttribute("listUsers", listUsers);

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//        System.out.println("This is the ID " + id);
        String name = customUserDetails.getFullName();

        String trainerIDD = String.valueOf(id);

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findByTrainerIDAndClassName(trainerIDD, className);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        return "trainer/trainer";
    }

    @PostMapping("/admin/filter_search")
    public String filterSearchAdmin(@ModelAttribute("bookClass") Classes classes, Model model,
                                    Authentication authentication, @RequestParam String accountStatus, RedirectAttributes redirectAttributes) {

        String section = "#admin-trainer-section";

//        return "admin/admin";
        redirectAttributes.addFlashAttribute("accountStatus", accountStatus);
        return "redirect:/admin_filter" + section;

    }

    @GetMapping("/admin_filter")
    public String listUsersForAdmin2(Model model, RedirectAttributes redirectAttributes,
                                     @ModelAttribute("accountStatus") final Object accountStatus) {
//        List<User> listUsers = userService.listAll();
//        model.addAttribute("listUsers", listUsers);

        model.addAttribute("activity", new Activity());

        List<Trainer> listTrainer = trainerRepository.findByAccountStatus((String) accountStatus);
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

//        System.out.println(accountStatus);

        return "admin/admin";
    }

    @GetMapping("/trainee/refresh_table")
    public String refreshTable(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        return "redirect:/trainee/table_refresh";
    }

    @GetMapping("/trainer/refresh_table")
    public String refreshTableTrainer(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        return "redirect:/trainer/table_refresh";
    }

    @GetMapping("/trainee/table_refresh")
    public String tableRefresh(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Classes> listClasses = classesRepository.findAll();
        model.addAttribute("listClasses", listClasses);

        model.addAttribute("classes", new Classes());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        model.addAttribute("trainee", new Trainee());

        CustomTraineeDetails customUserDetails = (CustomTraineeDetails) authentication.getPrincipal();

        Long id = customUserDetails.getID();

        String fullName = customUserDetails.getFullName();
        model.addAttribute("fullname", fullName);
//		System.out.println("This is the name " + id);

        String gh = String.valueOf(id);

        List<Bookings> listBookings = bookingsRepository.findByTraineeID(gh);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        return "trainee/trainee";
    }

    @GetMapping("/trainer/table_refresh")
    public String tableRefreshTrainer(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {

        //        List<Trainee> listUsers = userService.listTrainee();
//        model.addAttribute("listUsers", listUsers);

        CustomTrainerDetails customUserDetails = (CustomTrainerDetails) authentication.getPrincipal();
        Long id = customUserDetails.getID();
//        System.out.println("This is the ID " + id);
        String name = customUserDetails.getFullName();

        String trainerIDD = String.valueOf(id);

        List<Classes> listClasses = classesRepository.findByTrainerNameAndTrainerId(name, id);
        model.addAttribute("listClasses", listClasses);

        List<Bookings> listBookings = bookingsRepository.findByTrainerID(trainerIDD);
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("book", new Bookings());

        return "trainer/trainer";
    }

    @GetMapping("/admin/refresh_table")
    public String refreshTableAdmin(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {
        return "redirect:/admin/table_refresh";
    }

    @GetMapping("/admin/table_refresh")
    public String tableRefreshAdmin(@ModelAttribute("bookClass") Classes classes, Model model, Authentication authentication) {

        model.addAttribute("activity", new Activity());

        List<Trainer> listTrainer = userService.listTrainer();
        model.addAttribute("listTrainer", listTrainer);

        List<Trainee> listTrainee = userService.listTrainee();
        model.addAttribute("listTrainee", listTrainee);

        List<Bookings> listBookings = bookingsRepository.findAll();
        model.addAttribute("listBookings", listBookings);

        model.addAttribute("activity", new Activity());
        List<Activity> listActivities = activityRepository.findAll();
        model.addAttribute("listActivities", listActivities);

        return "admin/admin";
    }

}
