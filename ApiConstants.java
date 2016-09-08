package com.golden11.app.volleyWebservice;


import com.golden11.app.BuildConfig;

/**
 * The Class Constants.
 */
public class ApiConstants {


    //Header request param
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/x-www-form-urlencoded";

    //Request param
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_NAME = "name";
    public static final String KEY_MAIL = "mail";
    public static final String KEY_INVALID_COUNTRY = "field_user_country";
    public static final String KEY_VID = "vid";
    public static final String KEY_VID_TEAM = "2";
    public static final String KEY_VID_POSITION = "4";
    public static final String KEY_SOCIAL_LOGIN_DATA = "data";
    public static final String KEY_SOCIAL_LOGIN_IDENTIFIER = "identifier";
    public static final String KEY_SOCIAL_LOGIN_PHOTO_URL = "photoURL";
    public static final String KEY_SOCIAL_LOGIN_EMAIL = "email";
    public static final String KEY_SOCIAL_LOGIN_EMAIL_VERIFIED = "emailVerified";
    public static final String KEY_SOCIAL_LOGIN_PROVIDER = "provider";
    public static final String KEY_SOCIAL_LOGIN_FACEBOOK_PROVIDER = "Facebook";
    public static final String KEY_SOCIAL_LOGIN_TWITTER_PROVIDER = "Twitter";
    public static final String KEY_SOCIAL_LOGIN_GOOGLE_PROVIDER = "Google";
    public static final String KEY_PLAYER_TEAM_ID = "playerteam_id";
    public static final String KEY_TEAM_ID = "team_id";
    public static final String KEY_TRAINER_ID = "trainer_id";

    public static final String KEY_TEAM_PLAYERS = "players";
    public static final String KEY_BALANCE_DEDUCT = "balance_deduct";
    public static final String KEY_GOAL_KEEPER = "goalkeeper";
    public static final String KEY_DEFENDER = "defenders";
    public static final String KEY_MID_FILDER = "midfielders";
    public static final String KEY_FORWARD = "forward";
    public static final String KEY_BENCH_PLAYER = "benchplayers";
    public static final String KEY_CAPTAIN = "captain";

    //Unique team name check
    public static final String KEY_TEAM_NAME = "teamname";

    //Response param
    public static final String RES_SESSID = "sessid";
    public static final String RES_SESSION_NAME = "session_name";
    public static final String RES_TOKEN = "token";
    public static final String RES_USER = "user";
    //Time out for API
    public static final int TIME_OUT = 240000;
    public static final String KEY_PASS = "pass";
    public static final String KEY_STATUS = "status";
    public static final String KEY_NOTIFY = "notify";
    public static final String KEY_AGREE = "I_agree";
    public static final String KEY_COUNTRY = "field_user_country";
    public static final String KEY_MOBILE = "field_user_mobile";
    public static final String KEY_ROLES = "roles";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_RECEIVE_EMAIL = "field_user_receive_email";
    public static final String KEY_URL = "url";
    public static final String KEY_TEAMID = "teamid";
    public static final String KEY_NEW_PLAYER_ID = "new_player_id";
    public static final String KEY_OLD_PLAYER_ID = "rmpid";
    public static final String KEY_UPDATED_BALANCE = "updated_balance";
    public static final String KEY_GAME_INFO = "game_info";
    public static final String KEY_TITLE = "title";
    public static final String KEY_BODY = "body";
    public static String KEY_UND_JSON = "{\"und\":{\"value\":\"";
    public static String KEY_UND_JSON_END = "\"}}";
    public static String KEY_UND_JSON_ARRAY = "{\"und\":[{\"value\":\"";
    public static String KEY_UND_JSON_ARRAY_END = "\"}]}";


    //Substitute api request param
    public static final String KEY_BENCH_PID = "bench_pid";
    public static final String KEY_BENCH_PCODE = "bench_pcode";
    public static final String KEY_RPLACE_PID = "rplace_pid";
    public static final String KEY_RPLACE_PCODE = "rplace_pcode";


    public static String WEEKID = "weekid";
    public static String FORM_ERROR = "form_errors";
    public static String KEY_PICTURE_UPLOAD = "picture_upload";
    public static String KEY_PICTURE = "picture";
    public static String KEY_CHANGE_PICTURE = "change_picture";
    public static String KEY_FILE_NAME = "filename";
    //key for team list
    public static final String KEY_CURRENT_TEAM = "current_week";
    public static final String KEY_NEXT_TEAM = "next_week";
    //Get Notes from the parent object
    public static final String KEY_NODES = "nodes";
    //get tokem
    public static final String GET_TOKEN = "token";

    //for super league
    public static final String KEY_SUPER_LEAGUE_TEAM = "superleague";
    public static final String KEY_DATA = "data";
    public static final String KEY_SUPER_LEAGUE_NAME = "superleague_name";
    public static final String KEY_SUPER_LEAGUE_CREATE_INVITE_EMAIL_ID = "email";
    public static final String KEY_SUPER_LEAGUE_MEMBER_TEAM_ID = "member_team_id";
    public static final String KEY_SUPER_LEAGUE_PASSWORD = "superleague_password";
    public static final String KEY_SUCCESS = "success";
    public static final String KEY_PLAYER_DETAILS = "player_details";
    public static final String KEY_SUPER_LEAGUE_DETAILS = "superleague_details";

    //Domain url
//     public static String DOMAIN = "http://10.0.0.67/en/gl_services/";
    public static String DOMAIN = BuildConfig.HOST;//"http://144.48.250.26:8035/golden11test/en/gl_services/";

    public static final String UNIQUE_TEAM_NAME ="player/unique_teamname";
    //create team
    public static final String CREATE_TEAM_URL = "player/create_team";
    //fixture url
    public static final String FIXTURE_URL = "fixtures/data";
    //change password url
    public static final String CHANGE_PASSWORD = "/password_reset/";
    //get team list
    public static final String MY_TEAM = "player/get_myteams";
    //super power goal
    public static final String GOAL_SUPER_POWER = "player/goal_superpower";
    //super power assist
    public static final String ASSIST_SUPER_POWER = "player/assist_superpower";
    //super power card protection
    public static final String CARD_PROTECTION_SUPER_POWER = "player/card_protection_superpower";
    //Super power coach
    public static final String COACH_SUPER_POWER = "player/select_trainer";
    //super power assist
    public static final String CAPTAIN_SUPER_POWER = "player/captain_superpower";
    //make captain api call
    public static final String MAKE_CAPTAIN = "player/make_captain/";
    //super league team list
    public static final String SUPER_LEAGUE_TEAM_LIST = "player/superleague_player_teams";
    //make captain api call
    public static final String COACH_LISTING = DOMAIN + "trainers.json";
    //scouting detail url
    public static final String SCOUTING_PLAYER_LIST = DOMAIN + "real_players.json";
    //scouting detail url
    public static final String SCOUTING_DETAIL = DOMAIN + "player_info/";
    //use to join super league
    public static final String JOIN_SUPER_LEAGUE = "player/join_league";
    //use to create super league
    public static final String CREATE_SUPER_LEAGUE = "player/create_league";
    //News url
    public static final String NEWS_LIST = DOMAIN + "node?parameters[type]=news";
    //News url
    public static final String NEWS_LIST_NEW = DOMAIN + "news.json";
    //Get Team Listing
    public static final String GET_TEAM_OR_POSITION_LIST = DOMAIN + "taxonomy_vocabulary/getTree";
    //user domain url
    public static String DOMAIN_USER = DOMAIN + "user/";
    //Login Url
    public static final String LOGIN = DOMAIN_USER + "login.json";
    //Logout url
    public static final String LOGOUT = DOMAIN_USER + "logout.json";
    //forgot pass url
    public static final String FORGOT_PASSWORD = DOMAIN_USER + "request_new_password.json";
    //registe url
    public static final String REGISTER = DOMAIN_USER + "register.json";
    //sign up profile url
    public static String SIGN_UP_URL = DOMAIN_USER + "register.json";
    //social login url
    public static String SOCIAL_LOGIN_URL = DOMAIN + "player/social_login.json";
    //super league detail
    public static String SUPER_LEAGUE_DETAIL="player/superleague_player_list";
    //transfer player
    public static String TRANSFER_PLAYER="player/player_transfer";
    //Substute player
    public static String SUBSTITUTE_PLAYER="player/player_bench_substitution";
    //prizes url
    public static String PRIZES="prizes.json";
    //get config webservice
    public static String CONFIGURATION="config/global_configurations";
    //get ranking weekly
    public static String RANKING_WEEKLY="ranking/weekly.json";
    //get ranking GENERAL
    public static String RANKING_GENERAL="ranking/general.json";
    //get ranking superleague
    public static String RANKING_SUPER_LEAGUE="ranking/superleague.json";
    //get terms and condition
    public static final String TERMS_AND_CONDITION = DOMAIN+ "player/terms";
}
