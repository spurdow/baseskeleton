package com.github.spurdow.baseskeleton.base.utils.gc;

public interface GcTrigger {
    GcTrigger DEFAULT = new GcTrigger() {
        @Override
        public void runGc() {
            // Code taken from AOSP FinalizationTest:
            // https://android.googlesource.com/platform/libcore/+/master/support/src/test/java/libcore/
            // java/lang/ref/FinalizationTester.java
            // System.gc() does not garbage collect every time. Runtime.gc() is
            // more likely to perfom a gc.
            if(!isArtInUse()) {
                Runtime.getRuntime().gc();
                enqueueReferences();
                System.runFinalization();
            }
        }

        private void enqueueReferences() {
            // Hack. We don't have a programmatic way to wait for the reference queue daemon to move
            // references to the appropriate queues.
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new AssertionError();
            }
        }

        private boolean isArtInUse() {
            final String vmVersion = System.getProperty("java.vm.version");
            //Log.w(TAG , "Vm Version =>".concat(vmVersion));
            return vmVersion != null && vmVersion.startsWith("2");
        }
    };

    void runGc();
}