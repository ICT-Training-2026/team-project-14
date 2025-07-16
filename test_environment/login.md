# ログイン機能の解説
1. [概要](#概要)
2. [主な処理の流れ](#主な処理の流れ)


## 概要
general_function
京都研修で使うかもしれない機能を汎用化して作っておくことが目的になります。ここではログイン機能について説明します
## 主な処理の流れ
1. URLの記入

2. SpringFrameworkのControllerパッケージの中のログインページを表示するコントローラが実行

3. ログインページの表示,入力,login/へpost
4. Spring Securityが受け取り、認証(ここではSecurityConfig)
5. 認証処理の一部としてUserDetailsServiceが呼び出される。(UserDetailsServiceとはユーザ名（username）を受け取り、対応するユーザ情報を返すサービス)


