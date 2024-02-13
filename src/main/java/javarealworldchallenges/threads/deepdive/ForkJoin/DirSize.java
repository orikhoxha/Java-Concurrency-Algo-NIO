package javarealworldchallenges.threads.deepdive.ForkJoin;

import java.io.File;
import java.io.Serial;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.RecursiveTask;
import java.util.logging.Logger;

public class DirSize {

    private static final Logger LOGGER = Logger.getLogger(SumRecursiveTask.class.getName());

    private static class SizeOfFileTask extends RecursiveTask<Long> {

        @Serial
        private static final long serialVersionUID = -196522408291343951L;


        private final File file;

        public SizeOfFileTask(final File file) {
            this.file = Objects.requireNonNull(file);
        }
        @Override
        protected Long compute() {
            LOGGER.info(() -> "Comput size of " + file);

            if (file.isFile()) {
                return file.length();
            }

            final List<SizeOfFileTask> tasks = new ArrayList<>();
            final File[] children = file.listFiles();

            if (children != null) {
                for (File file : children) {
                    SizeOfFileTask task = new SizeOfFileTask(file);
                    task.fork();
                    tasks.add(task);
                }
            }

            long size = 0;
            for (final SizeOfFileTask task : tasks) {
                size += task.join();
            }

            return size;
        }
    }

}
