import json

import datetime
from string import whitespace
from tweepy.streaming import StreamListener
from tweepy import OAuthHandler
from tweepy import Stream
from textblob import TextBlob
from elasticsearch import Elasticsearch

elasticSearch = Elasticsearch()

class TweetStreamListener(StreamListener):

    # on success
    def on_data(self, data):

        dict_data = json.loads(data)
        tweet = TextBlob(dict_data["text"])

        # determine if sentiment is positive, negative, or neutral
        if tweet.sentiment.polarity < 0:
            sentiment = "negative"
        elif tweet.sentiment.polarity == 0:
            sentiment = "neutral"
        else:
            sentiment = "positive"

        #Remove space from user location for analysis
        removeSpace = dict_data["user"]["location"]
        removedSpace = ""
        if removeSpace is not None:
            removedSpace = ''.join(removeSpace.split())
            print(removedSpace)

        elasticSearch.index(index="tweet-sentiment",
                 doc_type="live-tweets",
                 body={"author": dict_data["user"]["screen_name"],
                       "date": dict_data["created_at"],
                       "location": removedSpace,  # location with removed spaces
                       "hashtags": dict_data['entities']['hashtags'],
                       "followers": dict_data["user"]["followers_count"],
                       "friends": dict_data["user"]["friends_count"],
                       "time_zone": dict_data["user"]["time_zone"],
                       "timestamp": dict_data["timestamp_ms"],
                       "lang": dict_data["user"]["lang"],
                       "datetime": datetime.datetime.now(),
                       "message": dict_data["text"],
                       "polarity": tweet.sentiment.polarity,
                       "subjectivity": tweet.sentiment.subjectivity, #positive, negative or neutral
                       "sentiment": sentiment})
        return True

    # on failure
    def on_error(self, status):
        print(status)

if __name__ == '__main__':

    listener = TweetStreamListener()

    auth = OAuthHandler("H0MB0igw64DmhPSEBLgRyuHbp", "oTUzVLLK1iJPCd95QJHI358s3zLlRzXaXhZf4gTxV0Fwq2nH8W")
    auth.set_access_token("2889696923-qqMTF0enWj46GQASHZMpLmZyHTpqz8XkBlHw0Ck", "Ph5DztHQ7KgT9CokBZVuAolmJxRuPKg5C6ZMfkcF6D3aa")

    stream = Stream(auth, listener)
    stream.filter(languages=['en'], track=['Java', 'Java Programming'], async=True)