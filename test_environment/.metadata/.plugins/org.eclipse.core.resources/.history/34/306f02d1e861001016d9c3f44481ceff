package com.generalfunction.demo.aop;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * このクラスはAOP（Aspect-Oriented Programming）のAspectであり、
 * 指定パッケージ内の全メソッドの実行前、正常終了後、例外発生後にログを出力することを目的としている。
 */
@Aspect
@Component
public class LoggingAspect {

    // ログ出力時に使用する日時フォーマットを固定化している
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 対象メソッドの実行直前に必ず呼び出されるAdvice。
     * execution式によりcom.JavaFramework.Reviewパッケージ及びそのサブパッケージ内のすべてのメソッドを対象とする。
     * @param joinPoint 呼び出されたメソッドの情報を保持
     */
    @Before("execution(* com.generalfunction.demo..*(..))")
    public void logBefore(JoinPoint joinPoint) {
        outputLog("メソッド開始", joinPoint, null);
    }

    /**
     * 対象メソッドが正常に終了した直後に呼び出されるAdvice。
     * 戻り値をキャッチしログに含める。
     * @param joinPoint 呼び出されたメソッドの情報
     * @param result 戻り値
     */
    @AfterReturning(pointcut = "execution(* com.generalfunction.demo..*(..))", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        outputLog("メソッド正常終了", joinPoint, result);
    }

    /**
     * 対象メソッドが例外をスローした直後に呼び出されるAdvice。
     * 例外情報をキャッチしログに含める。
     * @param joinPoint 呼び出されたメソッドの情報
     * @param ex 発生した例外
     */
    @AfterThrowing(pointcut = "execution(* com.generalfunction.demo..*(..))", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        outputLog("メソッド例外終了", joinPoint, ex);
    }

    /**
     * ログの共通出力メソッド。
     * ログメッセージは以下の情報を含む。
     * - ログ出力時刻（yyyy-MM-dd HH:mm:ss形式）
     * - メソッドの状態（開始、正常終了、例外終了）
     * - 対象クラス名とメソッド
     * - 例外終了時は例外の型とメッセージ
     * @param status ログ状態文字列
     * @param joinPoint 対象メソッド情報
     * @param detail 戻り値または例外オブジェクト
     */
    private void outputLog(String status, JoinPoint joinPoint, Object detail) {
        String timestamp = LocalDateTime.now().format(formatter);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        

        StringBuilder logBuilder = new StringBuilder();
        logBuilder.append(timestamp)
                  .append(" : ")
                  .append(status)
                  .append(" : ")
                  .append(className)
                  .append(".")
                  .append(methodName)
                  .append("()");


        if ("メソッド正常終了".equals(status)) {
     
        } else if ("メソッド例外終了".equals(status) && detail instanceof Throwable) {
            Throwable ex = (Throwable) detail;
            logBuilder.append(" : 例外=").append(ex.getClass().getSimpleName())
                      .append(" : メッセージ=").append(ex.getMessage());
        }

        System.out.println(logBuilder.toString());
    }
}