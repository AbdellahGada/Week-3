package comp74.week2.model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class Model {

    HashMap<Integer, Profile> profiles;
    HashMap<Integer, Posting> postings;
    HashMap<String, Profile> userMap;

    private static Integer nextProfileId = 100;
    private static Integer nextPostingId = 100;

    public Model() {
        super();
        profiles = new HashMap<>();
        userMap = new HashMap<>();
        postings = new HashMap<>();
    }

    // POST
    public Posting addPosting(Profile profile, Posting posting) {
        posting.setPostingId(nextPostingId++);
        posting.setDate(LocalDateTime.now());
        posting.setUserName(profile.getUserName());
        postings.put(posting.getPostingId(), posting);
        profile.addPosting(posting);
        return posting;
    }

    public Profile addProfile(Profile profile) {
        Profile newProfile = null;
        if (userMap.get(profile.getUserName()) == null) {
            newProfile = profile;
            newProfile.setProfileId(nextProfileId++);
            profiles.put(newProfile.getProfileId(), newProfile);
            userMap.put(newProfile.getUserName(), newProfile);
        }
        return newProfile;
    }

    // GET ALL

    public List<Profile> getProfiles() {
        return new ArrayList<>(profiles.values());
    }

    public List<Posting> getPostings() {
        return new ArrayList<>(postings.values());
    }

    // GET BY ???

    public Profile getProfileById(Integer id) {
        Profile profile = profiles.get(id);
        return profile;
    }

    public List<Profile> getProfilesByUserName(String userName) {
        Profile profile = userMap.get(userName);
        return new ArrayList<>(Arrays.asList(profile));
    }

}
