package org.dreamcat.vendor.aliyun;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.event.ProgressListener;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.function.Consumer;
import java.util.function.Function;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dreamcat.common.io.IOUtil;

/**
 * @author Jerry Will
 * @since 2021-06-29
 */
@Slf4j
@RequiredArgsConstructor
public class AliyunOssService {

    private final String endpoint; // oss-cn-xxx.aliyuncs.com
    private final String accessKeyId;
    private final String secretAccessKey;
    private final String securityToken;

    public boolean exist(String bucketName, String pathname) {
        return executeReturn(client ->
                client.doesObjectExist(bucketName, pathname));
    }

    // ---- ---- ---- ----    ---- ---- ---- ----    ---- ---- ---- ----

    public void upload(String bucketName, String key, String filePath, ProgressListener progressListener) {
        execute(client -> {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, new File(filePath));
            if (progressListener != null) {
                putObjectRequest.withProgressListener(progressListener);
            }
            client.putObject(putObjectRequest);
        });
    }

    public void upload(String bucketName, String key, InputStream input, ProgressListener progressListener) {
        execute(client -> {
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, input);
            if (progressListener != null) {
                putObjectRequest.withProgressListener(progressListener);
            }
            client.putObject(putObjectRequest);
        });
    }

    // ---- ---- ---- ----    ---- ---- ---- ----    ---- ---- ---- ----

    public void download(String bucketName, String key, OutputStream output, ProgressListener progressListener) {
        execute(client -> {
            OSSObject ossObject = client.getObject(bucketName, key);
            try {
                IOUtil.copy(ossObject.getObjectContent(), output);
                ossObject.getObjectContent().close();
            } catch (IOException e) {
                throw new ClientException(e);
            }
        });
    }

    // ==== ==== ==== ====    ==== ==== ==== ====    ==== ==== ==== ====

    public OSSClient createOSSClient() {
        CredentialsProvider credentialsProvider = new DefaultCredentialProvider(accessKeyId, secretAccessKey,
                securityToken);
        return new OSSClient(endpoint, credentialsProvider, null);
    }

    public String getBucketUrl(String bucketName) {
        return null; // fixme StringUtil.format("https://{}.{}", bucketName, endpoint);
    }

    private <T> T executeReturn(Function<OSSClient, T> fn) {
        OSSClient ossClient = createOSSClient();
        try {
            return fn.apply(ossClient);
        } finally {
            ossClient.shutdown();
        }
    }

    private void execute(Consumer<OSSClient> fn) {
        OSSClient ossClient = createOSSClient();
        try {
            fn.accept(ossClient);
        } finally {
            ossClient.shutdown();
        }
    }

}
