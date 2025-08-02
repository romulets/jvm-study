package nio;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MultipleWriteExample {

    public static void main(String[] args) throws IOException {
        try (ExecutorService pool = Executors.newFixedThreadPool(4, Thread.ofVirtual().factory())) {
            for (int i = 0; i < 10; i++) {
                Path path = Path.of("file" + i + ".txt");
                AsynchronousFileChannel channel = AsynchronousFileChannel.open(
                        path,
                        Set.of(StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                        pool
                );

                ByteBuffer buf = ByteBuffer.wrap(("Hey mate " + i).getBytes());

                channel.write(buf, 0, buf, new CompletionHandler<Integer, ByteBuffer>() {
                    @Override
                    public void completed(Integer result, ByteBuffer attachment) {
                        System.out.println("Written bytes to " + path);
                    }

                    @Override
                    public void failed(Throwable exc, ByteBuffer attachment) {
                        exc.printStackTrace();
                    }
                });
            }

            pool.shutdown();
        }

    }

}
