
CREATE OR REPLACE FUNCTION plot_word_cloud (tbl text,col text, lm int)
  RETURNS bytea
AS $$

import matplotlib
matplotlib.use('Agg') 
import matplotlib.pylab as plt
import io
import numpy as np
from wordcloud import WordCloud
#-----------------------------------------------------

sql = "SELECT  string_agg({0}, ' ') AS text FROM {1} LIMIT {2}".format(col,tbl,lm)

rv = plpy.execute(sql)


text = rv[0]['text']

# Generate a word cloud image
wordcloud = WordCloud().generate(text)

# Display the generated image:
# the matplotlib way:

plt.imshow(wordcloud)
plt.axis("off")

# take relative word frequencies into account, lower max_font_size
wordcloud = WordCloud(max_font_size=40, relative_scaling=.5).generate(text)
plt.figure()
plt.imshow(wordcloud)
plt.axis("off")


#--------------------------------------------------
img_buffer = io.BytesIO()
plt.savefig(img_buffer, format = 'png')
img_buffer.seek(0)
return img_buffer.getvalue()



$$ LANGUAGE plpython3u;



select plot_word_cloud('whv','description',1454630);


