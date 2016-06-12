# Import the necessary methods from tweepy library
from pymongo import MongoClient
import json
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
from textblob import TextBlob

ckey = ''
csecret = ''
atoken = ''
asecret = ''


# This is a basic listener that just prints received tweets to stdout.
class StdOutListener(StreamListener):
    client = MongoClient()

    def on_data(self, data):
        print(data)
        client = MongoClient()
        db = client['twitter_analyzed_db']
        collection = db['twitter_analyzed_collection']

        dict_data = json.loads(data)
        dict_data1 = str(dict_data)

        tweet = TextBlob(dict_data["text"])

        total_followers = dict_data["user"]["followers_count"]
        retweets = dict_data["retweet_count"]
        favorites = dict_data["favorite_count"]
        #popularity of the author
        listed_count = dict_data["user"]["listed_count"]
    #   tweets_retweets = dict_data["user"]["statuses_count"]
    #   friends_count = dict_data["user"]["friends_count"]

        #following: Favorites + RT’s + Replies ÷ Total followers
        print(total_followers, retweets, favorites, listed_count)
        if total_followers > 0:
            tweet_engagement = (replies + retweets/total_followers)
        else:
            tweet_engagement = 0.0

        # determine if sentiment is positive, negative, or neutral
        if tweet.sentiment.polarity < 0:
            sentiment = "negative"
        elif tweet.sentiment.polarity == 0:
            sentiment = "neutral"
        else:
            sentiment = "positive"

        print(tweet_engagement)
        dict_data['sentiment'] = sentiment
        dict_data['tweet_engagement'] = tweet_engagement

        collection.insert(dict_data)
       # collection.insert(lst)
#        db.collection.update({'_id': dict_data['_id']}, {"$set": {"sentiment": sentiment}})
        return True

    def on_error(self, status):
        print(status)


if __name__ == '__main__':
    # This handles Twitter authetification and the connection to Twitter Streaming API
    l = StdOutListener()
    auth = OAuthHandler(ckey, csecret)
    auth.set_access_token(atoken, asecret)
    stream = Stream(auth, l)

stream.filter(languages=['en'], track=['Java', 'Python', 'Javascript', 'c#'])

