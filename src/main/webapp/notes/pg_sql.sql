
CREATE FUNCTION drawchart ()
  RETURNS bytea
AS $$

import matplotlib
matplotlib.use('Agg') 
import pylab
import io

temp_data = {'x':[1,2,3],'y':[2,4,5]}
pylab.plot(temp_data['x'], temp_data['y'])

img_buffer = io.BytesIO()
pylab.savefig(img_buffer, format = 'png')
img_buffer.seek(0)
return img_buffer.getvalue()



$$ LANGUAGE plpython3u;



select drawchart();