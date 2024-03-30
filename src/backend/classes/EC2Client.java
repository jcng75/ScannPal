package backend.classes;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2ClientBuilder;
// import com.amazonaws.services.ec2.model.DescribeInstancesRequest;
// import com.amazonaws.services.ec2.model.DescribeInstancesResult;
// import com.amazonaws.services.ec2.model.Instance;
// import com.amazonaws.services.ec2.model.Reservation;
import com.amazonaws.services.ec2.model.*;

import io.github.cdimascio.dotenv.Dotenv;

public class EC2Client {
    
    private AmazonEC2 client;

    public EC2Client() {
        this.createClient();
    }

    // establishes an EC2 Client that can be used to interact an AWS EC2 instance
    private void createClient() {
        // Load environment variables from .env
        Dotenv dotenv = Dotenv.configure().load();
        String accessKey = dotenv.get("AWS_ACCESS_KEY_ID");
        String secretKey = dotenv.get("AWS_SECRET_ACCESS_KEY");

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        this.client = AmazonEC2ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();
    }

    // return the client to use directly in your code
    public AmazonEC2 getClient() {
        return this.client;
    }

    // lists all EC2 instances and some of their properties 
    public void listInstances() {
        AmazonEC2 client = this.client;
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResult result = client.describeInstances(request);

        for (Reservation reservation : result.getReservations()) {
            for (Instance instance : reservation.getInstances()) {
                System.out.println("Instance name: " + getName(instance));
                System.out.println("Instance ID: " + instance.getInstanceId());
                System.out.println("Instance public IP address: " + instance.getPublicIpAddress());
                System.out.println("Instance private IP address: " + instance.getPrivateIpAddress());
                System.out.println("Instance state: " + instance.getState().getName());
                System.out.println();
            }
        }
    }

    public List<String> getActiveInstances(){
        List<String> ips = new ArrayList<String>();
        AmazonEC2 client = this.client;
        DescribeInstancesRequest request = new DescribeInstancesRequest();
        DescribeInstancesResult result = client.describeInstances(request); 

        for (Reservation reservation : result.getReservations()){
            for (Instance instance : reservation.getInstances()){
               if (instance.getState().getName().equals("running")){
                ips.add(instance.getPrivateIpAddress());
               } 
            }
        }

        return ips;
    }

    // Returns the name of an EC2 instance (which is stored within the instance's tags)
    public String getName(Instance instance) {
        List<Tag> instanceTags = instance.getTags();
        for (Tag tag : instanceTags) {
            if (tag.getKey().equals("Name")) {
                return tag.getValue();
            }
        }
        return "None";
    }
}