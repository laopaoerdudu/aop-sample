package com.hk.aspect

import com.hk.internal.StopWatch
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Pointcut
import org.aspectj.lang.reflect.MethodSignature

@Aspect
class TraceAspect {
    @Pointcut(POINTCUT_METHOD)
    fun methodAnnotatedWithDebugTrace() {
        println("TraceAspect #POINTCUT_METHOD invoked")
    }

    @Pointcut(POINTCUT_CONSTRUCTOR)
    fun constructorAnnotatedDebugTrace() {
        println("TraceAspect #POINTCUT_CONSTRUCTOR invoked")
    }

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    fun weaveJoinPoint(joinPoint: ProceedingJoinPoint): Any? {
        try {
            (joinPoint.signature as? MethodSignature)?.let { methodSignature ->
                val className: String = methodSignature.declaringType.simpleName
                val methodName: String = methodSignature.name

                // Given
                val stopWatch = StopWatch()
                stopWatch.start()

                // When
                val result = joinPoint.proceed()

                // Then
                stopWatch.stop()

                println(
                    """
                    TraceAspect #weaveJoinPoint(...);
                    className -> $className;
                    methodName -> $methodName cost (${stopWatch.getTotalTimeMillis()}) ms;
                """.trimIndent()
                )
                return result
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
        return null
    }

    companion object {
        private const val POINTCUT_METHOD = "execution(@com.hk.annotation.DebugTrace * *(..))"
        private const val POINTCUT_CONSTRUCTOR =
            "execution(@com.hk.annotation.DebugTrace *.new(..))"
    }
}