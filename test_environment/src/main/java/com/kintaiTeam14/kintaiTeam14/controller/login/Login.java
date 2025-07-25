package com.kintaiTeam14.kintaiTeam14.controller.login;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.Data;
import lombok.RequiredArgsConstructor;

//ログイン機能
@Controller
@RequiredArgsConstructor
public class Login {
	//login画面の表示
    @GetMapping("/login") // "/login"へのGETリクエストを処理
    public String login() {
        return "login/login";  // login.htmlを表示
    }






















//いらない、ユーザ追加するときの参考に
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("form", new RegisterForm());
//        return "login/login"; // templates/login/register.htmlを表示
//    }

//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute("form") RegisterForm form,
//                               BindingResult bindingResult,
//                               Model model) {
//        // バリデーションエラーがあれば登録画面に戻る
//        if (bindingResult.hasErrors()) {
//            return "/login?";
//        }
//
//        // ユーザー名の重複チェック
//        if (userService.findByUsername(form.getUsername()) != null) {
//            bindingResult.rejectValue("username", "error.form", "既に登録済みのユーザー名です");
//            return "/login";
//        }
//
//        // ユーザー登録処理
//        userService.registerUser(form.getUsername(), form.getPassword(), form.getEmail());
//
//        // 登録完了後、ログイン画面へリダイレクト
//        return "redirect:/login";
//    }

    /**
     * ユーザー登録フォームのデータを格納するクラス。
     *
     * - username: ユーザー名（必須入力）
     * - password: パスワード（必須入力）
     *
     * バリデーションとして @NotBlank を付与しており、
     * 空文字やnullが入力された場合はエラーとなる。
     *
     * Lombokの @Data アノテーションにより、
     * 以下のメソッドが自動生成される。
     * - ゲッター / セッター
     * - equals / hashCode
     * - toString
     */
    @Data
    public static class RegisterForm {
        @jakarta.validation.constraints.NotBlank
        private String username;

        @jakarta.validation.constraints.NotBlank
        private String password;
        @jakarta.validation.constraints.NotBlank
        private String email;
    }

}
