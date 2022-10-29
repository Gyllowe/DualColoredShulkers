package net.gyllowe.dualcoloredshulkers._debugging;

import net.gyllowe.dualcoloredshulkers.DualColoredShulkers;

import java.util.ArrayList;
import java.util.List;

public class StackTracing {

    public static void Trace() {
        StringBuilder print = new StringBuilder();
        StackTraceElement[] stes = Thread.currentThread().getStackTrace();
        String className = "";
        int sameClassFor = 0;
        List<String> classMethodNames = new ArrayList<>();
        List<Boolean> isNativeMethod = new ArrayList<>();
        for(int i = 0; i <= stes.length; i++) {if(i != stes.length) {
                classMethodNames.add(stes[i].getMethodName());
                isNativeMethod.add(stes[i].isNativeMethod());

                if (className.equals( stes[i].getClassName() )) {
                    sameClassFor ++;
                    continue;
                }
                className = stes[i].getClassName();
            }

            print.append("\n");
            if(sameClassFor == 0)
                print.append(i).append("      =  ").append(className);
            else
                print.append(i - sameClassFor).append(" - ").append(i).append(" =  ").append(className);
            for(int j = 0; j < classMethodNames.size(); j++) {
                print.append("\n               ");
                print.append(classMethodNames.get(j)).append(", ").append(isNativeMethod.get(j));
            }

            sameClassFor = 0;
            classMethodNames.clear();
            isNativeMethod.clear();
        }
        DualColoredShulkers.LOGGER.info(print.toString());
    }
}
