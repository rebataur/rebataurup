
CREATE OR REPLACE FUNCTION drawchart (tbl text,col text)
  RETURNS bytea
AS $$

import matplotlib
matplotlib.use('Agg') 
import matplotlib.pylab as plt
import io
import numpy as np

#-----------------------------------------------------
sql = 'select {0} from {1} limit 10'.format(col,tbl)
rv = plpy.execute(sql)

sal = [x['salary']/100000 for x in rv ]

#-----------------------------------------------------

N = 50
x = sal
y = sal
colors = np.random.rand(N)
area = np.pi * (15 )**2  # 0 to 15 point radiuses

plt.scatter(x, y, s=area, c=colors, alpha=0.5)


#plt.figure()
#plt.plot(sal)
#plt.title("test")

#--------------------------------------------------
img_buffer = io.BytesIO()
plt.savefig(img_buffer, format = 'png')
img_buffer.seek(0)
return img_buffer.getvalue()



$$ LANGUAGE plpython3u;



select drawchart('salaries','salary');


